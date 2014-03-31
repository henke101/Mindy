package se.chalmers.mindy.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;


public class ExpanderAnimation extends Animation {

    private View animation;
    private LinearLayout.LayoutParams parameters;
    private int startMargin, lastMargin;
	
    public ExpanderAnimation(View view, int animationDuration) {

        setDuration(animationDuration);
        animation = view;
        parameters = (LinearLayout.LayoutParams) view.getLayoutParams();
        startMargin = parameters.bottomMargin;
        lastMargin = (startMargin == 0 ? (0- view.getHeight()) : 0);
        view.setVisibility(View.VISIBLE);
    }
	
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
 
        if (interpolatedTime < 1.0f) {
            parameters.bottomMargin = startMargin + (int) ((lastMargin - startMargin) * interpolatedTime);
            animation.requestLayout();
        }
    }
}
