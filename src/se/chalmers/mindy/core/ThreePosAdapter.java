package se.chalmers.mindy.core;

import java.util.ArrayList;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThreePosAdapter extends ArrayAdapter<ThreePosItem> {// implements Comparable{

	Context context;
	int layoutResourceId;
	int mCurrentlyFocusedId;
	private int mLastFocusedPosition;

	ArrayList<ThreePosItem> data;

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

		holder.dateLabel = (TextView) row.findViewById(R.id.date_label);
		holder.positiveOneLabel = (TextView) row.findViewById(R.id.positive_one_label);
		holder.positiveTwoLabel = (TextView) row.findViewById(R.id.positive_two_label);
		holder.positiveThreeLabel = (TextView) row.findViewById(R.id.positive_three_label);

		/**
		 * Added reversefunction, but it crashes because lack of good comparetoMethod
		 * Not sure what object to sort?
		 */
		// Collections.sort(data, Collections.reverseOrder());
		// Collections.sort(holder, Collections.reverseOrder());
		/**
		 * was wrong to get the(position). We need the 0 position!
		 */
		Typeface robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");

		holder.dateLabel.setText(data.get(position).getDate());
		holder.dateLabel.setTypeface(robotoLight);

		holder.positiveOneLabel.setText(data.get(position).getPositiveOne());
		holder.positiveOneLabel.setTypeface(robotoLight);

		holder.positiveTwoLabel.setText(data.get(position).getPositiveTwo());
		holder.positiveTwoLabel.setTypeface(robotoLight);

		holder.positiveThreeLabel.setText(data.get(position).getPositiveThree());
		holder.positiveThreeLabel.setTypeface(robotoLight);

		Log.d("Positive three ", "skriver ut : " + data.get(position).getPositiveThree());

		return row;
	}

	static class ThreePosItemHolder {

		TextView dateLabel, positiveOneLabel, positiveTwoLabel, positiveThreeLabel;
	}
	/**
	 * Need a compareTo method in order to implement comparable to be able to reverse our list of items
	 * But it is not working atm
	 * 
	 */
	// @Override
	// public int compareTo(Object arg0) {
	//
	// public int compareTo(ThreePosAdapter threePosAdapter) {
	// return threePosAdapter.compareTo(itemName);
	//
	// return 0;
	// }
}
