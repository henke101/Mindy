package se.chalmers.mindy.core;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.mindy.R;
import se.chalmers.mindy.view.IndexListItem;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * 
 * 
 * @author Viktor kerskog
 *
 */
public class IndexAdapter extends AbsListAdapter<IndexListItem> {

	private LayoutInflater mLayoutInflater;

	private Typeface robotoThin;
	private Typeface robotoLight;
	private int lastPosition;

	public IndexAdapter(final Context context, final ArrayList<IndexListItem> data) {
		// This will only work with the index_card_item XML, so having the
		// resID as dynamic merely adds complexity
		super(context, R.layout.index_card_item, data);

		robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");
		robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");

		mLayoutInflater = ((Activity) context).getLayoutInflater();
	}

	@Override
	public View getView(final int position, final View convertView, final ViewGroup parent) {
		LinearLayout row = (LinearLayout) convertView;

		IndexListItem item = data.get(position);

		if (row == null) {
			// This will only work with the index_card_item XML, so having the
			// resID as dynamic merely adds complexity
			row = (LinearLayout) mLayoutInflater.inflate(R.layout.index_card_item, parent, false);

		}

		// Initialize views
		IndexItemHolder holder = new IndexItemHolder();

		List<View> subviews = item.getSubviews();
		LinearLayout ll = (LinearLayout) row.findViewById(R.id.list_item_contents);

		// Clear the view
		ll.removeAllViews();
		for (View subview : subviews) {
			ll.addView(subview);
		}

		holder.title = (TextView) row.findViewById(R.id.item_title);
		holder.title.setText(item.getTitle());
		holder.title.setTypeface(robotoThin);
		holder.title.setOnClickListener(item.getTitleOnClickListener());

		holder.description = (TextView) row.findViewById(R.id.item_description);
		holder.description.setText(item.getDescription());
		holder.description.setTypeface(robotoLight);

		// If this view came from outside the screen, animate its entry
		if (position > lastPosition) {
			Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_from_bottom);
			row.startAnimation(animation);
		}
		lastPosition = position;

		// Set touch listener to detect swipes
		row.setOnTouchListener(getSwipeListenerInstance(context, (ListView) parent));

		return row;
	}

	static class IndexItemHolder {

		TextView title, description;
		List<View> subviews;
	}
}
