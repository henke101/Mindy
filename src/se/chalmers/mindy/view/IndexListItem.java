package se.chalmers.mindy.view;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 
 * Abstract parent class for list items displayed in index page. Any subclass must implement the getSubviews() method, 
 * which returns a list of subviews that will be displayed below the title and description in the index card.
 * 
 * @author Viktor �kerskog
 *
 */
public abstract class IndexListItem extends AbsListItem {

	public IndexListItem(Context context, String name, String description) {
		super(context, name, description);
	}

	public IndexListItem(Context context, int nameResId, int descriptionResId) {
		this(context, context.getResources().getString(nameResId), context.getResources().getString(descriptionResId));

	}

	/**
	 * Method used to get the subviews. These might be of any View type, the subclass is responsible 
	 * for any functionality they have.
	 */
	public abstract List<View> getSubviews();

	public abstract OnClickListener getTitleOnClickListener();

}
