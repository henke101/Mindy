package se.chalmers.mindy.core;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IndexAdapter extends ArrayAdapter<IndexItem> {

	Context context;
	IndexItem data[] = null;

	Typeface robotoThin;
	Typeface robotoLight;

	public IndexAdapter(final Context context, final ArrayList<IndexItem> data) {
		this(context, data.toArray(new IndexItem[data.size()]));
	}

	public IndexAdapter(final Context context, final IndexItem[] data) {
		// This will only work with the index_card_item XML, so having the
		// resID as dynamic merely adds complexity
		super(context, R.layout.index_card_item, data);
		this.context = context;
		this.data = data;

		robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");
		robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		LinearLayout row = (LinearLayout) convertView;
		IndexItemHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();

			// This will only work with the index_card_item XML, so having the
			// resID as dynamic merely adds complexity
			row = (LinearLayout) inflater.inflate(R.layout.index_card_item, parent, false);

			holder = new IndexItemHolder();

			holder.title = (TextView) row.findViewById(R.id.item_title);
			holder.title.setText(data[position].getTitle());
			holder.title.setTypeface(robotoThin);

			holder.description = (TextView) row.findViewById(R.id.item_description);
			holder.description.setText(data[position].getDescription());
			holder.description.setTypeface(robotoLight);

			List<View> subviews = data[position].getSubviews();
			LinearLayout ll = (LinearLayout) row.findViewById(R.id.list_item_contents);
			for (View subview : subviews) {
				ll.addView(subview);
			}
		}

		return row;
	}

	static class IndexItemHolder {

		TextView title, description;
		List<View> subviews;
	}
}
