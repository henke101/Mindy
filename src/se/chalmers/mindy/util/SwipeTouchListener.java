package se.chalmers.mindy.util;

import java.util.HashMap;

import se.chalmers.mindy.core.AbsListAdapter;
import se.chalmers.mindy.pojo.AbsListItem;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

/**
 * Handle touch events to fade/move dragged items as they are swiped out
 */
public class SwipeTouchListener implements View.OnTouchListener {

	float mDownX;
	private int mSwipeSlop = -1;
	private boolean mItemPressed;
	private Context mContext;
	private boolean mSwiping;
	private ListView mListView;

	HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();
	private AbsListAdapter<AbsListItem> mAdapter;

	private static final int SWIPE_DURATION = 250;
	private static final int MOVE_DURATION = 150;

	public SwipeTouchListener(Context context, ListView listView) {
		mContext = context;
		mListView = listView;
		if (listView.getAdapter() instanceof HeaderViewListAdapter) {
			mAdapter = (AbsListAdapter<AbsListItem>) ((HeaderViewListAdapter) listView.getAdapter()).getWrappedAdapter();
		} else {
			mAdapter = (AbsListAdapter<AbsListItem>) listView.getAdapter();
		}
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		if (mSwipeSlop < 0) {
			mSwipeSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mItemPressed) {
				// Multi-item swipes not handled
				return false;
			}
			mItemPressed = true;
			mDownX = event.getX();
			break;
		case MotionEvent.ACTION_CANCEL:
			v.setAlpha(1);
			v.setTranslationX(0);
			mItemPressed = false;
			break;
		case MotionEvent.ACTION_MOVE: {
			float x = event.getX() + v.getTranslationX();
			float deltaX = x - mDownX;
			float deltaXAbs = Math.abs(deltaX);
			if (!mSwiping) {
				if (deltaXAbs > mSwipeSlop) {
					mSwiping = true;
					mListView.requestDisallowInterceptTouchEvent(true);
					// mBackgroundContainer.showBackground(v.getTop(), v.getHeight());
				}
			}
			if (mSwiping) {
				v.setTranslationX(x - mDownX);
				v.setAlpha(1 - deltaXAbs / v.getWidth());
			}
		}
			break;
		case MotionEvent.ACTION_UP: {
			// User let go - figure out whether to animate the view out, or back into place
			if (mSwiping) {
				float x = event.getX() + v.getTranslationX();
				float deltaX = x - mDownX;
				float deltaXAbs = Math.abs(deltaX);
				float fractionCovered;
				float endX;
				float endAlpha;
				final boolean remove;
				if (deltaXAbs > v.getWidth() / 4) {
					// Greater than a quarter of the width - animate it out
					fractionCovered = deltaXAbs / v.getWidth();
					endX = deltaX < 0 ? -v.getWidth() : v.getWidth();
					endAlpha = 0;
					remove = true;
				} else {
					// Not far enough - animate it back
					fractionCovered = 1 - deltaXAbs / v.getWidth();
					endX = 0;
					endAlpha = 1;
					remove = false;
				}
				// Animate position and alpha of swiped item
				// NOTE: This is a simplified version of swipe behavior, for the
				// purposes of this demo about animation. A real version should use
				// velocity (via the VelocityTracker class) to send the item off or
				// back at an appropriate speed.
				long duration = (int) ((1 - fractionCovered) * SWIPE_DURATION);
				mListView.setEnabled(false);

				if (Tools.isRuntimePastIceCreamSandwich()) {

					v.animate().setDuration(duration).alpha(endAlpha).translationX(endX).withEndAction(new Runnable() {
						@Override
						public void run() {
							// Restore animated values
							v.setAlpha(1);
							v.setTranslationX(0);
							if (remove) {
								animateRemoval(mListView, v);
							} else {
								// mBackgroundContainer.hideBackground();
								mSwiping = false;
								mListView.setEnabled(true);
							}
						}
					});

				} else {

					v.animate().setListener(new AnimatorListenerAdapter() {

						@Override
						public void onAnimationEnd(Animator animation) {
							// Restore animated values
							v.setAlpha(1);
							v.setTranslationX(0);
							if (remove) {
								animateRemoval(mListView, v);
							} else {
								// mBackgroundContainer.hideBackground();
								mSwiping = false;
								mListView.setEnabled(true);
							}
						}

					}).setDuration(duration).alpha(endAlpha).translationX(endX);
				}

			}
		}
			mItemPressed = false;
			break;
		default:
			return false;
		}
		return true;
	}

	/**
	 * This method animates all other views in the ListView container (not including ignoreView)
	 * into their final positions. It is called after ignoreView has been removed from the
	 * adapter, but before layout has been run. The approach here is to figure out where
	 * everything is now, then allow layout to run, then figure out where everything is after
	 * layout, and then to run animations between all of those start/end positions.
	 */
	private void animateRemoval(final ListView listview, View viewToRemove) {
		int firstVisiblePosition = listview.getFirstVisiblePosition();
		for (int i = 0; i < listview.getChildCount(); ++i) {
			View child = listview.getChildAt(i);
			if (child != viewToRemove) {
				int position = firstVisiblePosition + i;
				long itemId = mAdapter.getItemId(position);
				mItemIdTopMap.put(itemId, child.getTop());
			}
		}
		// Delete the item from the adapter
		// -1 is because the position index includes the list header
		int position = mListView.getPositionForView(viewToRemove) - 1;
		mAdapter.remove(position);

		final ViewTreeObserver observer = listview.getViewTreeObserver();
		observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@SuppressLint("NewApi")
			@Override
			public boolean onPreDraw() {
				observer.removeOnPreDrawListener(this);
				boolean firstAnimation = true;
				int firstVisiblePosition = listview.getFirstVisiblePosition();
				for (int i = 0; i < listview.getChildCount(); ++i) {
					final View child = listview.getChildAt(i);
					int position = firstVisiblePosition + i;
					long itemId = mAdapter.getItemId(position);
					Integer startTop = mItemIdTopMap.get(itemId);
					int top = child.getTop();
					if (startTop != null) {
						if (startTop != top) {
							int delta = startTop - top;
							child.setTranslationY(delta);
							child.animate().setDuration(MOVE_DURATION).translationY(0);

							if (firstAnimation) {
								if (Tools.isRuntimePastIceCreamSandwich()) {
									child.animate().withEndAction(new Runnable() {
										@Override
										public void run() {
											// mBackgroundContainer.hideBackground();
											mSwiping = false;
											mListView.setEnabled(true);
										}
									});
								}
								firstAnimation = false;
							}
						}
					} else {
						// Animate new views along with the others. The catch is that they did not
						// exist in the start state, so we must calculate their starting position
						// based on neighboring views.
						int childHeight = child.getHeight() + listview.getDividerHeight();
						startTop = top + (i > 0 ? childHeight : -childHeight);
						int delta = startTop - top;
						child.setTranslationY(delta);
						child.animate().setDuration(MOVE_DURATION).translationY(0);
						if (firstAnimation) {
							child.animate().withEndAction(new Runnable() {
								@Override
								public void run() {
									// mBackgroundContainer.hideBackground();
									mSwiping = false;
									mListView.setEnabled(true);
								}
							});
							firstAnimation = false;
						}
					}
				}
				mItemIdTopMap.clear();
				return true;
			}
		});
	}
}
