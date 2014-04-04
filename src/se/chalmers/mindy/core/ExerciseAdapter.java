package se.chalmers.mindy.core;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExerciseAdapter extends ArrayAdapter<ExerciseItem>{
	
	Context context;
	int layoutResourceId;
	ExerciseItem data[] = null;
	
	public ExerciseAdapter(final Context context, final int layoutResourceId, final ExerciseItem[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View row = convertView;
		ExerciseItemHolder holder = null;
		
		if(row == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ExerciseItemHolder();

			holder.exercise = (TextView) row.findViewById(R.id.exercise_label);
			holder.exercise.setText(data[position].getName());
			
			holder.description = (TextView) row.findViewById(R.id.description_label);
			holder.description.setText(data[position].getDescription());

		}
		
		holder = (ExerciseItemHolder) row.getTag();
		
		return row;
	}
	
	static class ExerciseItemHolder {
		
		TextView exercise, description;
	}
}
