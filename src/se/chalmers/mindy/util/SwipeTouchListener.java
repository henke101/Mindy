package se.chalmers.mindy.util;

import java.util.HashMap;

import se.chalmers.mindy.core.AbsListAdapter;
import se.chalmers.mindy.view.AbsListItem;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;

/**
 * Class responsible for handling touch events to fade/move dragged items as they are swiped out.
 * AbsListAdapter class has a convenience method for getting an instance of this class.
 * 
 * @author Viktor Åkerskog
 *
 */
public class SwipeTouchListener implements View.OnTouchListener {

	float mDownX;
	private int mSwipeSlop = -1;
	private boolean mItemPressed;
	private Context mContext;
	private boolean mSwiping;
	private ListView mListView;
	private VelocityTracker mVelocityTracker;

	HashMap<Long, Integer> mItemIdTopMap = new HashMap<Long, Integer>();
	private AbsListAdapter<AbsListItem> mAdapter;

	private static final int SWIPE_DURATION = 500;
	private static final int MOVE_DURATION = 300;
	private static final int MIN_VELOCITY = 400;
	private static final float MAX_VELOCITY = 1500;

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
			Log.d("Derp", "Down");
			if (mItemPressed) {
				// Multi-item swipes not handled
				return false;
			}

			// Get the velocity tracker, in order to track swipes made by the user
			if (mVelocityTracker == null) {
				mVelocityTracker = VelocityTracker.obtain();
			} else {
				mVelocityTracker.clear();
			}
			// Add this movement event to tracker
			mVelocityTracker.addMovement(event);

			mItemPressed = true;

			// Get the X position for the view when it was pressed
			mDownX = event.getX();
			break;
		case MotionEvent.ACTION_CANCEL:
			// Swipe was cancelled, restore everything and recycle the velocity tracker
			v.setAlpha(1);
			v.setTranslationX(0);
			mItemPressed = false;
			if (mSwiping) {
				mVelocityTracker.recycle();
			}
			break;
		case MotionEvent.ACTION_MOVE: {

			// Add this movement event to tracker
			mVelocityTracker.addMovement(event);
			// Compute the current X (horizontal) velocity. Any calls to getXVelocity() or getYVelocity() will give the values computed here
			mVelocityTracker.computeCurrentVelocity(1000);

			// Get current X position
			float x = event.getX() + v.getTranslationX();

			// Get (absolute) X difference
			float deltaX = x - mDownX;
			float deltaXAbs = Math.abs(deltaX);
			if (!mSwiping) {
				if (deltaXAbs > mSwipeSlop) {
					// Request the listview to avoid interfering with this view
					mSwiping = true;
					mListView.requestDisallowInterceptTouchEvent(true);
				}
			}
			if (mSwiping) {
				// Move the view and set the opacity to reflect the swipe
				v.setTranslationX(x - mDownX);
				v.setAlpha(1 - deltaXAbs / v.getWidth());
			}
		}
			break;
		case MotionEvent.ACTION_UP: {
			Log.d("Derp", "Up");

			// If the user meant to press the item, let the system handle click instead of handling swipe here
			// User let go - figure out whether to animate the view out, or back into place
			if (mSwiping) {
				float x = event.getX() + v.getTranslationX();
				float deltaX = x - mDownX;
				float deltaXAbs = Math.abs(deltaX);
				float fractionCovered;
				float endX;
				float endAlpha;
				final boolean remove;

				if (deltaXAbs > v.getWidth() / 2 || (int) Math.abs(mVelocityTracker.getXVelocity()) > MIN_VELOCITY) {
					// Greater than half the width - animate it out
					// Will also animate out if the swipe velocity was high enough - regardless of swipe distance
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
				// Depends upon the velocity of the swipe only if the velocity is between MINIMUM_VELOCITY and MAX_VELOCITY,
				// else fixed values in order to avoid too slow or too fast animation
				float currentXVelocityAbs = Math.abs(mVelocityTracker.getXVelocity());
				long duration = (long) (currentXVelocityAbs > MIN_VELOCITY ? 100000 / Math.min(currentXVelocityAbs, MAX_VELOCITY) : (1 - fractionCovered)
						* SWIPE_DURATION);

				// Disable the list view so that the user cannot interrupt the animation through other interactions
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
								mSwiping = false;
								mListView.setEnabled(true);
							}
						}
					});

				} else {

					// For Ice Cream Sandwich we have to specify the end action with a listener
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
				// Cache the current top position of this view in the map, before removing the view we want gone
				mItemIdTopMap.put(itemId, child.getTop());
			}
		}
		// Delete the item from the adapter
		// -1 is because the position index includes the list header
		int position = mListView.getPositionForView(viewToRemove) - 1;

		// remove() method calls notifyDataSetChanged() by itself, no need to do it here
		mAdapter.remove(position);

		final ViewTreeObserver observer = listview.getViewTreeObserver();
		observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
			@SuppressLint("NewApi")
			@Override
			public boolean onPreDraw() {
				// We no longer want to catch onPreDraw events
				observer.removeOnPreDrawListener(this);

				boolean firstAnimation = true;
				int firstVisiblePosition = listview.getFirstVisiblePosition();

				for (int i = 0; i < listview.getChildCount(); ++i) {
					// Get the current view
					final View child = listview.getChildAt(i);

					// Get the view's position in the adapter
					int position = firstVisiblePosition + i;

					// Get the ID of this view from the list adapter
					long itemId = mAdapter.getItemId(position);

					// Get the starting top position from the position map
					Integer startTop = mItemIdTopMap.get(itemId);
					int top = child.getTop();

					// View was visible before, just needs to be moved
					if (startTop != null) {
						if (startTop != top) {
							int delta = startTop - top;
							child.setTranslationY(delta);

							// For Ice Cream Sandwich we have to specify the end action with a listener
							if (!Tools.isRuntimePastIceCreamSandwich()) {

								if (firstAnimation) {
									child.animate().setListener(new AnimatorListenerAdapter() {

										@Override
										public void onAnimationEnd(Animator animation) {
											mSwiping = false;
											mListView.setEnabled(true);
										}
									});
									firstAnimation = false;
								}

								child.animate().setDuration(MOVE_DURATION).translationY(0);
							} else {
								child.animate().setDuration(MOVE_DURATION).translationY(0);

								if (firstAnimation) {
									child.animate().withEndAction(new Runnable() {
										@Override
										public void run() {
											mSwiping = false;
											mListView.setEnabled(true);
										}
									});
								}
								firstAnimation = false;
							}
						}
					} else {
						// Animate new views along with the others. They did not exist in the start state,
						// so we must calculate their starting position based on neighboring views.
						int childHeight = child.getHeight() + listview.getDividerHeight();
						startTop = top + (i > 0 ? childHeight : -childHeight);
						int delta = startTop - top;
						child.setTranslationY(delta);
						// For Ice Cream Sandwich we have to specify the end action with a listener
						if (!Tools.isRuntimePastIceCreamSandwich()) {

							if (firstAnimation) {
								child.animate().setListener(new AnimatorListenerAdapter() {

									@Override
									public void onAnimationEnd(Animator animation) {
										mSwiping = false;
										mListView.setEnabled(true);
									}
								});
								firstAnimation = false;
							}

							child.animate().setDuration(MOVE_DURATION).translationY(0);
						} else {
							child.animate().setDuration(MOVE_DURATION).translationY(0);

							if (firstAnimation) {
								child.animate().withEndAction(new Runnable() {
									@Override
									public void run() {
										mSwiping = false;
										mListView.setEnabled(true);
									}
								});
							}
							firstAnimation = false;
						}
						/*
						child.animate().setDuration(MOVE_DURATION).translationY(0);
						if (firstAnimation) {
							child.animate().withEndAction(new Runnable() {
								@Override
								public void run() {
									mSwiping = false;
									mListView.setEnabled(true);
								}
							});
							firstAnimation = false;
						}*/
					}
				}
				mItemIdTopMap.clear();
				return true;
			}
		});
	}
}
