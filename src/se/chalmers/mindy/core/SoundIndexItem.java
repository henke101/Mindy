package se.chalmers.mindy.core;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;

public class SoundIndexItem extends IndexItem {

	public SoundIndexItem(String name, String description, List<OnClickListener> buttonListeners) {
		super(name, description, buttonListeners);
	}

	@Override
	public List<OnClickListener> getButtonListeners() {
		List<OnClickListener> listeners = new ArrayList<View.OnClickListener>();

		listeners.add(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return listeners;
	}

}
