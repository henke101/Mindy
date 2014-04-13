package se.chalmers.mindy.view.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class ExpandAnimation extends Animation {
	private final View mAnimatedView;
	private final LinearLayout.LayoutParams mViewLayoutParams;
	private final int mMarginStart, mMarginEnd;
	private boolean mIsVisibleAfter = false;
	private boolean mWasEndedAlready = false;

	/**
	 * Initialize the animation
	 * 
	 * @param view
	 *            The layout we want to animate
	 * @param duration
	 *            The duration of the animation, in ms
	 */
	public ExpandAnimation(final View view, final int duration) {

		setDuration(duration);
		mAnimatedView = view;

		mViewLayoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();

		// decide to show or hide the view
		mIsVisibleAfter = view.getVisibility() == View.VISIBLE;

		mMarginStart = mViewLayoutParams.bottomMargin;
		mMarginEnd = mMarginStart == 0 ? 0 - view.getHeight() : 0;

		view.setVisibility(View.VISIBLE);
		view.setAlpha(0.0f);
	}

	@Override
	protected void applyTransformation(final float interpolatedTime, final Transformation t) {
		super.applyTransformation(interpolatedTime, t);

		if (interpolatedTime < 1.0f) {

			// Calculating the new bottom margin, and setting it
			mViewLayoutParams.bottomMargin = mMarginStart + (int) ((mMarginEnd - mMarginStart) * interpolatedTime);

			// Invalidating the layout, making us seeing the changes we made
			mAnimatedView.requestLayout();

			mAnimatedView.setAlpha(1.0f * interpolatedTime);
			

			// Making sure we didn't run the ending before (it happens!)
		} else if (!mWasEndedAlready) {
			mViewLayoutParams.bottomMargin = mMarginEnd;
			mAnimatedView.requestLayout();

			if (mIsVisibleAfter) {
				mAnimatedView.setVisibility(View.GONE);
			}
			mWasEndedAlready = true;
		}
	}
}
