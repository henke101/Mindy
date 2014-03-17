package se.chalmers.mindy.core;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThreePosAdapter extends ArrayAdapter<ThreePosItem>{
	
	Context context;
	int layoutResourceId;
	ThreePosItem[] data = null;
	
	public ThreePosAdapter(final Context context, final int layoutResourceId, final ThreePosItem[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View row = convertView;
		ThreePosItemHolder holder = null;
		
		if(row == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ThreePosItemHolder();

			holder.exercise = (TextView) row.findViewById(R.id.exercise_label);
			holder.exercise.setText(data[position].getDate());
			
			holder.description = (TextView) row.findViewById(R.id.description_label);
			holder.description.setText(data[position].getDate());

		}
		
		holder = (ThreePosItemHolder) row.getTag();
		
		return row;
	}
	
	static class ThreePosItemHolder {
		
		TextView exercise, description;
	}
}

