package se.chalmers.mindy.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import se.chalmers.mindy.R;
import se.chalmers.mindy.view.DiaryItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DiaryAdapter extends ArrayAdapter<DiaryItem> {

	Context context;
	ArrayList<DiaryItem> data = null;
	private Typeface robotoThin;
	private Typeface robotoLight;
	private LayoutInflater mLayoutInflater;
	private SimpleDateFormat mDateFormat;

	public DiaryAdapter(final Context context, final ArrayList<DiaryItem> data) {
		super(context, R.layout.diary_list_item, data);
		this.context = context;
		this.data = data;
		robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");
		robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
		mLayoutInflater = ((Activity) context).getLayoutInflater();
		mDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View row = convertView;
		DiaryItemHolder holder = new DiaryItemHolder();
		if (row == null) {
			row = mLayoutInflater.inflate(R.layout.diary_list_item, parent, false);

		}

		holder.titleLabel = (TextView) row.findViewById(R.id.title_label);
		holder.titleLabel.setTypeface(robotoThin);

		holder.bodyLabel = (TextView) row.findViewById(R.id.body_label);
		holder.bodyLabel.setTypeface(robotoLight);

		holder.dateLabel = (TextView) row.findViewById(R.id.date_label);
		holder.dateLabel.setTypeface(robotoLight);

		if (data.get(position) != null) {
			holder.titleLabel.setText(data.get(position).getTitle() == null ? "" : data.get(position).getTitle());
			holder.bodyLabel.setText(data.get(position).getBody() == null ? "" : data.get(position).getBody());

			if (data.get(position).getDate() != null) {
				String stringDate = mDateFormat.format(data.get(position).getDate().getTime());
				holder.dateLabel.setText(stringDate);
			}
		}

		return row;
	}

	static class DiaryItemHolder {

		TextView titleLabel, bodyLabel, dateLabel;
	}
}
