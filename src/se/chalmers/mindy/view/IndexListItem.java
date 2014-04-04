package se.chalmers.mindy.view;

import java.util.List;

import android.content.Context;
import android.view.View;

/**
 * 
 * Abstract parent class for list items displayed in index page. Any subclass must implement the getSubviews() method, 
 * which returns a list of subviews that will be displayed below the title and description in the index card.
 * 
 * @author Viktor Åkerskog
 *
 */
public abstract class IndexListItem extends AbsListItem {

	public IndexListItem(Context context, String name, String description) {
		super(context, name, description);
	}

	/**
	 * Method used to get the subviews. These might be of any View type, the subclass is responsible 
	 * for any functionality they have.
	 */
	public abstract List<View> getSubviews();

}
