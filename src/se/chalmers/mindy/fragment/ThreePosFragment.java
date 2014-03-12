package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ThreePosFragment extends ListFragment{

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;
	TextView expanded;
	
	public void onAttach(final Activity activity){
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = sharedPrefs.edit();
		
	}
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		ListView listView = getListView();
		View headerView = mActivity.getLayoutInflater().inflate(R.layout.three_positive_things, null);
		TextView titleView = (TextView) headerView.findViewById(R.id.pos_title);
		titleView.setText(R.string.app_name);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_light.ttf"));
		
		listView.addHeaderView(headerView);
		expanded = (TextView) headerView.findViewById(R.id.expanded);
		expanded.setVisibility(View.GONE);

	}


}
