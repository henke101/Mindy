package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_about, null);
		TextView tViewAbout = (TextView) view.findViewById(R.id.about);
		TextView tViewAboutVersion = (TextView) view
				.findViewById(R.id.about_version);
		TextView tViewAboutText = (TextView) view.findViewById(R.id.about_text);
		TextView tViewAboutDevelopers = (TextView) view
				.findViewById(R.id.about_developers);
		
		Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_light.ttf");
		Typeface robotoCondensedLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_condensed_light.ttf");
		
		tViewAbout.setTypeface(robotoLight);
		tViewAboutVersion.setTypeface(robotoCondensedLight);
		tViewAboutText.setTypeface(robotoCondensedLight);
		tViewAboutDevelopers.setTypeface(robotoCondensedLight);
		return view;
	}

}
