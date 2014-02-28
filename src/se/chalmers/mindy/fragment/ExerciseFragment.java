package se.chalmers.mindy.fragment;


import java.util.List;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.ExerciseAdapter;
import se.chalmers.mindy.core.ExerciseItem;
import se.chalmers.mindy.core.MainActivity;
import android.app.Activity;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ExerciseFragment extends ListFragment{

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;

	ExerciseItem[] exItemList = {new ExerciseItem("hej","hej",new Color()), new ExerciseItem("d�","d�", new Color())};	
	
	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = sharedPrefs.edit();	
	
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView listView = getListView();

		View headerView = mActivity.getLayoutInflater().inflate(R.layout.list_header, null);
		TextView titleView = (TextView) headerView.findViewById(R.id.header_title);
		titleView.setText(R.string.app_name);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_light.ttf"));

		listView.addHeaderView(headerView);
		
		/** Setting the list adapter for the ListFragment */
		ExerciseAdapter adapter = new ExerciseAdapter(mActivity.getLayoutInflater().getContext(), R.layout.card_item, exItemList);
		setListAdapter(adapter);

	}
	
}
