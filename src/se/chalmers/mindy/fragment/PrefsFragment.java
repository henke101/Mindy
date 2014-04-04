package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.view.View;
import android.widget.Toast;

public class PrefsFragment extends PreferenceFragment implements OnPreferenceChangeListener {
	private static SwitchPreference swchFetch;
	private static String toastText = "";
	private MainActivity activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		swchFetch = (SwitchPreference) findPreference("pref_notes");
		swchFetch.setOnPreferenceChangeListener(this);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		activity.setActionBarBackgroundTransparency(255);
		view.setPadding(0, 160, 0, 0);
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference == swchFetch) {
			// if to ON can add more here for other stuff if it's a switch preference
			if ((Boolean) newValue) {
				Toast.makeText(getActivity(), "Notiser ON" + toastText, Toast.LENGTH_SHORT).show();
			}
			// if OFF can add more here for other stuff if it's a switch preference
			else {
				Toast.makeText(getActivity(), "Notiser OFF", Toast.LENGTH_SHORT).show();
			}
		}
		return true;
	}
}
