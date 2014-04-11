package se.chalmers.mindy.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.fragment.DiaryEditFragment;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import se.chalmers.mindy.view.DiaryItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DiaryAdapter extends AbsListAdapter<DiaryItem> implements OnClickListener {

	Context context;
	ArrayList<DiaryItem> data = null;
	ArrayList<Integer> ids = null;
	private Typeface robotoThin;
	private Typeface robotoLight;
	private LayoutInflater mLayoutInflater;
	private SimpleDateFormat mDateFormat;
	private MindyDatabaseAdapter mDbAdapter;

	public DiaryAdapter(final Context context, MindyDatabaseAdapter dbAdapter) {
		super(context, R.layout.diary_list_item, dbAdapter.fetchAllNotes());
		this.context = context;
		robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");
		robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
		mLayoutInflater = ((Activity) context).getLayoutInflater();
		mDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		mDbAdapter = dbAdapter;
		ids = mDbAdapter.fetchAllNoteIds();
		data = mDbAdapter.fetchAllNotes();
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
		holder.titleLabel.setTag(position);
		holder.titleLabel.setOnClickListener(this);

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

		// Currently does not work properly
		// row.setOnTouchListener(getSwipeListenerInstance(context, (ListView) parent));

		return row;
	}

	public boolean isListEmpty() {
		return ids.size() <= 0;
	}

	public int getIdAt(int position) {
		return ids.get(position);
	}

	@Override
	public DiaryItem remove(int position) {
		int headerCompensatedPosition = position - 1;
		// Remove the item at position from data list
		DiaryItem item = data.get(headerCompensatedPosition);

		if (!isListEmpty()) {
			mDbAdapter.deleteNote(mDbAdapter.fetchAllNoteIds().get(headerCompensatedPosition));
			data = mDbAdapter.fetchAllNotes();
			ids = mDbAdapter.fetchAllNoteIds();

			Toast.makeText(context, R.string.diary_entry_deleted, Toast.LENGTH_SHORT).show();

		} else {
			Toast.makeText(context, R.string.diary_could_not_delete, Toast.LENGTH_SHORT).show();
		}

		// Redraw list
		notifyDataSetChanged();

		return item;
	}

	static class DiaryItemHolder {

		TextView titleLabel, bodyLabel, dateLabel;
	}

	@Override
	public void onClick(View v) {
		DiaryEditFragment fragment = new DiaryEditFragment();

		if (!isListEmpty()) {
			Integer index = (Integer) v.getTag();

			Bundle bundle = new Bundle();
			bundle.putInt("rowID", getIdAt(index));
			fragment.setArguments(bundle);
		}
		((MainActivity) context).pushFragment(fragment);

	}
}
