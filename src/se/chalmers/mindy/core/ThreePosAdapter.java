package se.chalmers.mindy.core;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.content.Context;
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
		ThreePosItemHolder holder = new ThreePosItemHolder();

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

		}

		holder.positiveOne = (TextView) row.findViewById(R.id.positive_one_label);
		holder.positiveTwo = (TextView) row.findViewById(R.id.positive_two_label);
		holder.positiveThree = (TextView) row.findViewById(R.id.positive_three_label);

		holder.positiveOne.setText(data[position].getPositiveOne());
		holder.positiveTwo.setText(data[position].getPositiveTwo());
		holder.positiveThree.setText(data[position].getPositiveThree());

		return row;
	}

	static class ThreePosItemHolder {

		TextView positiveOne, positiveTwo, positiveThree;
	}
}
