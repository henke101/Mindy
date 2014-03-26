package se.chalmers.mindy.core;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;

public abstract class AbsListAdapter<E> extends ArrayAdapter<E> {

	protected final Context context;
	protected final ArrayList<E> data;

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

	@Override
	public boolean hasStableIds() {
		return true;
	}

}
