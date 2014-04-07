package se.chalmers.mindy.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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

public class ThreePosAdapter extends ArrayAdapter<ThreePosItem> {

	Context context;
	int layoutResourceId;
	int mCurrentlyFocusedId;
	ArrayList<ThreePosItem> data;
	String stringDate;
	SimpleDateFormat dfDate;
	
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
	 * Change fonts in date and input strings in each card
	 */

		Typeface robotoConLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_condensed_light.ttf");

//		holder.dateLabel.setText(data.get(position).getDate());
//		holder.dateLabel.setTypeface(robotoConLight);
		

		Typeface robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
		
		dfDate = new SimpleDateFormat("yyyy-MM-dd");
		stringDate = dfDate.format(data.get(position).getDate().getTime());
		holder.dateLabel.setText(stringDate);
		holder.dateLabel.setTypeface(robotoLight);


		holder.positiveOneLabel.setText(data.get(position).getPositiveOne());
		holder.positiveOneLabel.setTypeface(robotoConLight);

		holder.positiveTwoLabel.setText(data.get(position).getPositiveTwo());
		holder.positiveTwoLabel.setTypeface(robotoConLight);

		holder.positiveThreeLabel.setText(data.get(position).getPositiveThree());
		holder.positiveThreeLabel.setTypeface(robotoConLight);

		Log.d("Positive three ", "skriver ut : " + data.get(position).getPositiveThree());

		return row;
	}

	static class ThreePosItemHolder {

		TextView dateLabel, positiveTextLabel, positiveOneLabel, positiveTwoLabel, positiveThreeLabel;
	}

}
