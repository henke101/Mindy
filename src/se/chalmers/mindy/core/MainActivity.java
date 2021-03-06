package se.chalmers.mindy.core;

import se.chalmers.mindy.R;
import se.chalmers.mindy.fragment.AboutFragment;
import se.chalmers.mindy.fragment.DiaryListFragment;
import se.chalmers.mindy.fragment.EvaluationFragment;
import se.chalmers.mindy.fragment.ExerciseFragment;
import se.chalmers.mindy.fragment.IndexFragment;
import se.chalmers.mindy.fragment.PrefsFragment;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import se.chalmers.mindy.util.Tools;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private String[] sectionNames;
	private Drawable mActionBarBackgroundDrawable;
	private int mActionBarAlpha;

	private AboutFragment fragmentAbout;
	private PrefsFragment fragmentSettings;
	private ExerciseFragment fragmentExercise;
	private IndexFragment fragmentIndex;
	private FragmentManager fragmentManager;

	private MediaPlayer mMediaPlayer;

	private MindyDatabaseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Resources res = getResources();

		BitmapDrawable glowingLogo = new BitmapDrawable(res, Tools.setLogoGlow(res, R.drawable.ic_test2_launcher));
		getActionBar().setLogo(glowingLogo);

		mActionBarBackgroundDrawable = res.getDrawable(R.drawable.action_bar_background);
		mActionBarBackgroundDrawable.setColorFilter(res.getColor(R.color.action_bar_background), PorterDuff.Mode.MULTIPLY);
		mActionBarBackgroundDrawable.setAlpha(0);

		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(mActionBarBackgroundDrawable);
		ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);

		fragmentManager = getFragmentManager();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Get the section name array for Navigation Drawer
		sectionNames = res.getStringArray(R.array.section_names);

		// TODO TEMP
		adapter = new MindyDatabaseAdapter(this);
		adapter.open();

		final int actionBarTitle = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
		final TextView title = (TextView) getWindow().findViewById(actionBarTitle);

		if (title != null) {
			Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/roboto_light.ttf");
			title.setTypeface(typeface);
			title.setTextSize(22.0f);
			title.setPadding(5, 1, 0, 0);
			title.setTextColor(res.getColor(R.color.action_bar_title));
		}

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, sectionNames));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectItem(position);
			}
		});

		if (getFragmentManager().findFragmentById(R.id.content_frame) == null) {
			selectItem(0);
		}

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			/** Called when a drawer has settled in a completely open state. */
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				mActionBarBackgroundDrawable.setAlpha(Math.max(Math.min((int) (slideOffset * 1000), 255), mActionBarAlpha));
			}

			@Override
			public void onDrawerStateChanged(int newState) {

			}

		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);

		if (!sharedPref.contains("started")) {
			Fragment fragmentEvaluation = new EvaluationFragment();
			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentEvaluation).commit();

			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putInt("started", 1);
			editor.commit();
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		adapter.close();

	}

	/**
	 * Swaps fragments in the main content view
	 * 
	 * OBS
	 * 
	 * Will be done dynamically, add the rest of the fragments
	 * */
	private void selectItem(int position) {
		// Pop the whole fragment backstack
		fragmentManager.popBackStack(null, 0);

		if (position == 0) {
			// Create a new fragment and specify the planet to show based on
			// position
			if (fragmentIndex == null) {
				fragmentIndex = new IndexFragment();
			}

			// Insert the fragment by replacing any existing fragment
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentIndex).commit();
		}

		if (position == 1) {
			// Create a new fragment and specify the planet to show based on
			// position

			fragmentExercise = new ExerciseFragment();

			// Insert the fragment by replacing any existing fragment
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentExercise).commit();
		}

		/*
		 * Need to implement a SettingsFragment
		 */
		// if (position == 2) {
		// // Create a new fragment and specify the planet to show based on
		// // position
		// fragmentSettings = new PrefsFragment();
		//
		// // Insert the fragment by replacing any existing fragment
		// fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentSettings).commit();
		// }

		if (position == 2) {
			// Diary
			ListFragment fragmentDiary = new DiaryListFragment();
			// Insert the fragment by replacing any existing fragment
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentDiary).commit();
		}
		if (position == 3) {
			fragmentAbout = new AboutFragment();

			// Insert the fragment by replacing any existing fragment
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentAbout).commit();
		}
		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(sectionNames[position]);
		mDrawerLayout.closeDrawer(mDrawerList);

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * Sets the transparency of the action bar depending on the scroll position of a list view. Used by fragments.
	 * 
	 * @param listView the list to depend transparency upon
	 * @param listHeaderHeight the height of the header in the list
	 */
	public void setActionBarTransparencyFromListViewPosition(AbsListView listView, int listHeaderHeight) {

		// Get the first visible child
		int firstVisiblePosition = listView.getFirstVisiblePosition();
		View child = listView.getChildAt(firstVisiblePosition);

		final int headerHeight = listHeaderHeight - getActionBar().getHeight();
		final float ratio = (float) Math.min(Math.max(Math.abs(child.getTop()) + Math.max(child.getHeight(), 500) * firstVisiblePosition, 0), headerHeight)
				/ headerHeight;
		final int newAlpha = (int) (ratio * 255);

		setActionBarBackgroundTransparency(newAlpha);

	}

	public void setActionBarBackgroundTransparency(int alpha) {

		mActionBarBackgroundDrawable.setAlpha(alpha);
		mActionBarAlpha = alpha;
	}

	public void pushFragment(Fragment fragment) {
		// Insert the fragment by replacing any existing fragment
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(R.id.content_frame, fragment);
		// Add fragment to back stack, in order to be able to use the back button
		ft.addToBackStack(null);
		ft.commit();
	}

	public MindyDatabaseAdapter getMindyDb() {
		return adapter;
	}

}
