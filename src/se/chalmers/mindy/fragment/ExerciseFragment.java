package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.ExerciseAdapter;
import se.chalmers.mindy.core.ExerciseItem;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.util.Tools;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ExerciseFragment extends ListFragment {

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;

	ExerciseItem[] exItemList = { new ExerciseItem("Sšmnpiller", "fšrklarande text", new Color()),
			new ExerciseItem("Pomodoroklocka", "Fšrklarande text", new Color()), new ExerciseItem("Tre Positiva saker", "Fšrklarande text", new Color()) };

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
		listView.setBackgroundColor(Color.rgb(226, 226, 226));
		listView.setDividerHeight(0);

		View headerView = mActivity.getLayoutInflater().inflate(R.layout.list_header, null);
		TextView titleView = (TextView) headerView.findViewById(R.id.header_title);
		titleView.setText(R.string.exercises);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));

		ImageView imageView = (ImageView) headerView.findViewById(R.id.header_background);
		Tools.setTwoStepBitmapBackground(mActivity, R.drawable.ice, imageView);

		listView.addHeaderView(headerView);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (position == 1) {
					Fragment fragmentSleepingPill = new SleepingPillFragment();
					// Insert the fragment by replacing any existing fragment
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentSleepingPill).commit();
					System.out.println("Sleeping pressed");
				}

				/**
				 * Rickard: insert your fragment here
				
				if(position ==2){
					Fragment fragmentSleepingPill = new SleepingPillFragment();
					// Insert the fragment by replacing any existing fragment
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentSleepingPill).commit();
					System.out.println("Sleeping pressed");
				}
				*/
				if (position == 3) {
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
