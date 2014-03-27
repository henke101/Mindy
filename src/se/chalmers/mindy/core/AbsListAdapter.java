package se.chalmers.mindy.core;

import java.util.ArrayList;

import se.chalmers.mindy.util.SwipeTouchListener;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class AbsListAdapter<E> extends ArrayAdapter<E> {

	protected final Context context;
	protected final ArrayList<E> data;
	private SwipeTouchListener mSwipeListener;

	public AbsListAdapter(Context context, int resource, ArrayList<E> data) {
		super(context, resource, data);

		this.context = context;
		this.data = data;
	}

	public E remove(int position) {
		E item = data.get(position);
		data.remove(position);
		notifyDataSetChanged();

		return item;

	}

	@Override
	public Context getContext() {
		return context;
	}

	public ArrayList<E> addItem(E item) {
		data.add(item);
		notifyDataSetChanged();

		return data;
	}

	public ArrayList<E> addItem(E item, int position) {
		data.add(position, item);
		notifyDataSetChanged();

		return data;
	}

	public ArrayList<E> getData() {
		return data;
	}

	public SwipeTouchListener getSwipeListenerInstance(Context context, ListView listView) {
		if (mSwipeListener == null) {
			mSwipeListener = new SwipeTouchListener(context, listView);
		}

		return mSwipeListener;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

}
