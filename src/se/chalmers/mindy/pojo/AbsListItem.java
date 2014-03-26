package se.chalmers.mindy.pojo;

import android.content.Context;

public abstract class AbsListItem {

	protected Context context;
	protected String title, description;

	public AbsListItem(Context context, String name, String description) {
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
}
