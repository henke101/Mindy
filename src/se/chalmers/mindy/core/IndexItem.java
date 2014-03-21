package se.chalmers.mindy.core;

import java.util.List;

import android.content.Context;
import android.view.View;

public abstract class IndexItem {

	private Context context;
	private String title, description;

	public IndexItem(Context context, String name, String description) {
		super();

		this.context = context;

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

	/**
	 * Method used to get the button listeners 
	 * @return
	 */
	public abstract List<View> getSubviews();

}
