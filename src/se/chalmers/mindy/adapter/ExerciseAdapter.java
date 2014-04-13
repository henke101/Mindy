package se.chalmers.mindy.adapter;

import se.chalmers.mindy.R;
import se.chalmers.mindy.view.ExerciseItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExerciseAdapter extends ArrayAdapter<ExerciseItem> {

	Context context;
	int layoutResourceId;
	ExerciseItem data[] = null;
	private Typeface robotoThin;
	private Typeface robotoLight;
	private LayoutInflater mLayoutInflater;
	private int lastPosition;

	public ExerciseAdapter(final Context context, final int layoutResourceId, final ExerciseItem[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");
		robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
		mLayoutInflater = ((Activity) context).getLayoutInflater();
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View row = convertView;
		ExerciseItemHolder holder = new ExerciseItemHolder();

		if (row == null) {
			row = mLayoutInflater.inflate(layoutResourceId, parent, false);
		}

		holder.exercise = (TextView) row.findViewById(R.id.exercise_label);
		holder.exercise.setTypeface(robotoThin);

		holder.description = (TextView) row.findViewById(R.id.description_label);
		holder.description.setTypeface(robotoLight);

		holder.exercise.setText(data[position].getName());
		holder.description.setText(data[position].getDescription());

		// If this view came from outside the screen, animate its entry
		if (position > lastPosition) {
			Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_from_bottom);
			row.startAnimation(animation);
		}
		lastPosition = position;

		return row;
	}

	static class ExerciseItemHolder {

		TextView exercise, description;
	}
}
