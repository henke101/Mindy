package se.chalmers.mindy.core;

import java.util.ArrayList;
import java.util.Collections;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ThreePosAdapter extends ArrayAdapter<ThreePosItem> {//implements Comparable{

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
		
		
		/**
		 * Vad vill vi sortera? -Vill sortera hur våra cards sätts ut i layouten
		 * 
		 * vill sortera varje card. men varje card är tre holders. hur kan jag jämföra två objekt i comparemetoden?
		 */
		holder.date = (TextView) row.findViewById(R.id.date_label);
		holder.positiveOne = (TextView) row.findViewById(R.id.positive_one_label);
		holder.positiveTwo = (TextView) row.findViewById(R.id.positive_two_label);
		holder.positiveThree = (TextView) row.findViewById(R.id.positive_three_label);

		/**
		 * Added reversefunction, but it crashes because lack of good comparetoMethod
		 * Not sure what object to sort?
		 */
//		Collections.sort(data, Collections.reverseOrder());
	//	Collections.sort(holder, Collections.reverseOrder());
		/**
		 * was wrong to get the(position). We need the 0 position!
		 */
		holder.date.setText(data.get(position).getDate());
		holder.positiveOne.setText(data.get(position).getPositiveOne()); 
		holder.positiveTwo.setText(data.get(position).getPositiveTwo()); 
		holder.positiveThree.setText(data.get(position).getPositiveThree());
		Log.d("Positive three ", "skriver ut : " + data.get(position).getPositiveThree());
		
		return row;
	}
	

	static class ThreePosItemHolder {

		TextView date, positiveOne, positiveTwo, positiveThree;
	}
/**
 * Need a compareTo method in order to implement comparable to be able to reverse our list of items
 * But it is not working atm
 * 
 */
//	@Override
//	public int compareTo(Object arg0) {
//		
//		public int compareTo(ThreePosAdapter threePosAdapter) {
//	        return threePosAdapter.compareTo(itemName);
//	
//		return 0;
//	}
}
