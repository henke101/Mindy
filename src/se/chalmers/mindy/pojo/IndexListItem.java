package se.chalmers.mindy.pojo;

import java.util.List;

import android.content.Context;
import android.view.View;

public abstract class IndexListItem extends AbsListItem {

	public IndexListItem(Context context, String name, String description) {
		super(context, name, description);
	}

	/**
	 * Method used to get the button listeners 
	 * @return
	 */
	public abstract List<View> getSubviews();

}
