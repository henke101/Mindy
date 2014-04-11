package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import android.app.Activity;
import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
	private MainActivity activity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about, null);
		TextView tViewAbout = (TextView) view.findViewById(R.id.about);
		TextView tViewAboutVersion = (TextView) view.findViewById(R.id.about_version);
		TextView tViewAboutText = (TextView) view.findViewById(R.id.about_text);
		TextView tViewAboutDevelopers = (TextView) view.findViewById(R.id.about_developers);

		Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_light.ttf");
		Typeface robotoThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_thin.ttf");
		Typeface robotoCondensedLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_condensed_light.ttf");

		tViewAbout.setTypeface(robotoThin);
		tViewAboutVersion.setTypeface(robotoCondensedLight);
		tViewAboutText.setTypeface(robotoLight);
		tViewAboutDevelopers.setTypeface(robotoCondensedLight);

		PackageInfo pInfo;
		try {
			pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
			String version = pInfo.versionName;
			tViewAboutVersion.setText("v. " + version);
		} catch (NameNotFoundException e) {
		}
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		activity.setActionBarBackgroundTransparency(255);
	}
}
