package se.chalmers.mindy.core;

import java.util.ArrayList;

import se.chalmers.mindy.pojo.IndexItem;
import android.content.Context;
import android.widget.ArrayAdapter;

public abstract class MindyListAdapter extends ArrayAdapter<IndexItem> {

	public MindyListAdapter(Context context, int resource, ArrayList<IndexItem> data) {
		super(context, resource, data);
	}

}
