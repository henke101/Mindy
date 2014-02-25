package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PrefsFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		//Load the preferences from an XML source
		addPreferencesFromResource(R.xml.preferences);
	}
	
	
}
