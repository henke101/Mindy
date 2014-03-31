package se.chalmers.mindy.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

public class CollapseAnimation extends Animation {

	private View animation;
    private LinearLayout.LayoutParams parameters;
    private int initialHeight;
    
    public CollapseAnimation(View view, int animationDuration){
    	
    	setDuration(animationDuration);
    	animation = view;
    	parameters = (LinearLayout.LayoutParams) view.getLayoutParams();
        initialHeight = animation.getMeasuredHeight();
        view.setVisibility(View.VISIBLE);
        
    }
    
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t){
        if(interpolatedTime == 1){
            animation.setVisibility(View.GONE);
        }else{
            parameters.height = initialHeight - (int)(initialHeight * interpolatedTime);
            animation.requestLayout();
        }
    	 
    }
    
}
