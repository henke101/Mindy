package se.chalmers.mindy.view;

import android.content.Context;
import android.graphics.Typeface;

/**
 * 
 * Abstract parent class for list items. In order to use the SwipeTouchListener, the list adapter used must be an AbsListAdapter subclass with
 * AbsListItem items in it.  
 * 
 * @author Viktor Åkerskog
 *
 */
public abstract class AbsListItem {

	protected Context context;
	protected String title, description;

	protected Typeface robotoLightCondensed;
	protected Typeface robotoLight;
	protected Typeface robotoThin;

	public AbsListItem(Context context, String name, String description) {
		super();
		this.context = context;

		robotoLightCondensed = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_condensed_light.ttf");
		robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
		robotoThin = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");

		title = name;
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Context getContext() {
		return context;
	}
}
