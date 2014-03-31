package se.chalmers.mindy.core;

import java.util.ArrayList;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThreePosAdapter extends ArrayAdapter<ThreePosItem> {

	Context context;
	int layoutResourceId;
	int mCurrentlyFocusedId;
	private int mLastFocusedPosition;

	ArrayList<ThreePosItem> data = null;

	public ThreePosAdapter(final Context context, final int layoutResourceId, final ArrayList<ThreePosItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View row = convertView;
		ThreePosItemHolder holder = new ThreePosItemHolder();

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

		}

		holder.positiveOne = (TextView) row.findViewById(R.id.positive_one_label);
		holder.positiveTwo = (TextView) row.findViewById(R.id.positive_two_label);
		holder.positiveThree = (TextView) row.findViewById(R.id.positive_three_label);

		holder.positiveOne.setText(data.get(position).getPositiveOne()); 
		holder.positiveTwo.setText(data.get(position).getPositiveTwo()); 
		holder.positiveThree.setText(data.get(position).getPositiveThree());
		Log.d("Positive three ", "skriver ut : " + data.get(position).getPositiveThree());
    	
		return row;
	}

	static class ThreePosItemHolder {

		TextView positiveOne, positiveTwo, positiveThree;
	}
}
