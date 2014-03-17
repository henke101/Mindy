package se.chalmers.mindy.core;

import java.util.List;

import android.view.View.OnClickListener;

public abstract class IndexItem {

	private String name, description;

	public IndexItem(String name, String description, List<OnClickListener> buttonListeners) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Method used to get the button listeners 
	 * @return
	 */
	public abstract List<OnClickListener> getButtonListeners();

}
