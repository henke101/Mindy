package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.ExerciseAdapter;
import se.chalmers.mindy.core.ExerciseItem;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.util.Tools;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ExerciseFragment extends ListFragment implements OnScrollListener {

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;
	String[] exerciseName;
	String[] exerciseDescription;
	ExerciseItem[] exItemList;

	private View mListHeader;


//	ExerciseItem[] exItemList = {new ExerciseItem(R.string.sleeping_pill, R.string.short_desc_sleeping, new Color()), new ExerciseItem(R.string.pomodoro_name, R.string.short_desc_pomodoro, new Color()), new ExerciseItem(R.string.three_pos_name, R.string.short_desc_three_pos,new Color())};	
	

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);

		editor = sharedPrefs.edit();

	}

	@Override
	public void onPause() {
		super.onPause();

		Log.d("Here", "in onpause");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView listView = getListView();
		listView.setBackgroundColor(mActivity.getResources().getColor(R.color.bg_color_grey));
		listView.setDividerHeight(0);
		listView.setOnScrollListener(this);

		mListHeader = mActivity.getLayoutInflater().inflate(R.layout.list_header, null);
		TextView titleView = (TextView) mListHeader.findViewById(R.id.header_title);
		titleView.setText(R.string.exercises);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));

		ImageView imageView = (ImageView) mListHeader.findViewById(R.id.header_background);
		Tools.setTwoStepBitmapBackground(mActivity, R.drawable.fluff, imageView);

		listView.addHeaderView(mListHeader);

		exerciseName = getResources().getStringArray(R.array.exercise_names);
		exerciseDescription = getResources().getStringArray(R.array.exercise_descriptions);
		exItemList = new ExerciseItem[] { new ExerciseItem(exerciseName[0], exerciseDescription[0], new Color()),
				new ExerciseItem(exerciseName[1], exerciseDescription[1], new Color()), new ExerciseItem(exerciseName[2], exerciseDescription[2], new Color()),
				new ExerciseItem(exerciseName[3], exerciseDescription[3], new Color()) };

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (position == 1) {
					Fragment fragmentSleepingPill = new AudioExerciseFragment();
					Bundle bundle = new Bundle();
					bundle.putInt("audioID", R.raw.sleeping_pill);
					bundle.putInt("titleID", R.string.sleeping_pill);
					bundle.putInt("infoID", R.string.long_desc_sleeping_pill);
					fragmentSleepingPill.setArguments(bundle);

					// Insert the fragment by replacing any existing fragment
					mActivity.setFragment(fragmentSleepingPill);

				} else if (position == 2) {
					mActivity.setFragment(new PomodoroFragment());

				} else if (position == 3) {
					mActivity.setFragment(new ThreePosFragment());

				} else if (position == 4) {
					Fragment fragmentBodyFocus = new AudioExerciseFragment();
					Bundle bundle = new Bundle();
					bundle.putInt("audioID", R.raw.body_focus);
					bundle.putInt("titleID", R.string.body_focus);
					bundle.putInt("infoID", R.string.long_desc_body_focus);
					fragmentBodyFocus.setArguments(bundle);

					mActivity.setFragment(fragmentBodyFocus);
				}
			}
		});

		/** Setting the list adapter for the ListFragment */
		ExerciseAdapter adapter = new ExerciseAdapter(mActivity.getLayoutInflater().getContext(), R.layout.card_item, exItemList);
		setListAdapter(adapter);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (visibleItemCount > firstVisibleItem) {
			mActivity.setActionBarTransparencyFromListViewPosition(view, mListHeader.getHeight());
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Do nothing
	}
}
