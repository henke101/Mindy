package se.chalmers.mindy.fragment;


import java.util.List;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.ExerciseAdapter;
import se.chalmers.mindy.core.ExerciseItem;
import se.chalmers.mindy.core.MainActivity;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ExerciseFragment extends ListFragment{

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;

	ExerciseItem[] exItemList = {new ExerciseItem("Somnpiller","forklarande text",new Color()), new ExerciseItem("då","då", new Color()), new ExerciseItem("PosNamn","PosDesc",new Color())};	
	
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

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if(position ==1){
					Fragment fragmentSleepingPill = new SleepingPillFragment();
					// Insert the fragment by replacing any existing fragment
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentSleepingPill).commit();
					System.out.println("Sleeping pressed");
				}
				
				if(position ==3){
					ListFragment fragmentThreePos = new ThreePosFragment();
					// Insert the fragment by replacing any existing fragment
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentThreePos).commit();
					System.out.println("Pos Pressed");
				}
			}
		});

		/** Setting the list adapter for the ListFragment */
		ExerciseAdapter adapter = new ExerciseAdapter(mActivity.getLayoutInflater().getContext(), R.layout.card_item, exItemList);
		setListAdapter(adapter);

	}

}
