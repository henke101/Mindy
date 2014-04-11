package se.chalmers.mindy.adapter;

import se.chalmers.mindy.R;
import se.chalmers.mindy.view.ExerciseItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExerciseAdapter extends ArrayAdapter<ExerciseItem> {

	Context context;
	int layoutResourceId;
	ExerciseItem data[] = null;
	private Typeface robotoThin;
	private Typeface robotoLight;
	private LayoutInflater mLayoutInflater;

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

		return row;
	}

	static class ExerciseItemHolder {

		TextView exercise, description;
	}
}
