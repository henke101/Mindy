package se.chalmers.mindy.core;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.ExerciseAdapter.ExerciseItemHolder;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class IndexAdapter extends ArrayAdapter<ExerciseItem> {

	Context context;
	ExerciseItem data[] = null;

	public IndexAdapter(final Context context, final ExerciseItem[] data) {
		// This will only work with the index_card_item XML, so having the
		// resID as dynamic merely adds complexity
		super(context, R.layout.index_card_item, data);
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		View row = convertView;
		ExerciseItemHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			// This will only work with the index_card_item XML, so having the
			// resID as dynamic merely adds complexity
			row = inflater.inflate(R.layout.index_card_item, parent, false);

			holder = new ExerciseItemHolder();

			holder.exercise = (TextView) row.findViewById(R.id.item_title);
			holder.exercise.setText(data[position].getName());

			holder.description = (TextView) row.findViewById(R.id.item_description);
			holder.description.setText(data[position].getName());

		}

		holder = (ExerciseItemHolder) row.getTag();

		return row;
	}

	static class IndexItemHolder {

		TextView title, description;
	}
}
