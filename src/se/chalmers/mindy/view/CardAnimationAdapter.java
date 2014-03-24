package se.chalmers.mindy.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

public class CardAnimationAdapter extends AnimationAdapter {

	public CardAnimationAdapter(BaseAdapter baseAdapter) {
		super(baseAdapter);
	}

	@Override
	public Animator[] getAnimators(ViewGroup parent, View view) {
		Animator bottomInAnimator = ObjectAnimator.ofFloat(view, "translationY", 500, 0);
		return new Animator[] { bottomInAnimator };
	}

	@Override
	protected long getAnimationDelayMillis() {
		return DEFAULTANIMATIONDELAYMILLIS;
	}

	@Override
	protected long getAnimationDurationMillis() {
		return 500;
	}
}