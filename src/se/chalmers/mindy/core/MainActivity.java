package se.chalmers.mindy.core;

import se.chalmers.mindy.R;
import se.chalmers.mindy.fragment.AboutFragment;
import se.chalmers.mindy.fragment.ExerciseFragment;
import se.chalmers.mindy.fragment.IndexFragment;
import se.chalmers.mindy.fragment.PrefsFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
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

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private String[] sectionNames;
	private Drawable mActionBarBackgroundDrawable;
	private int mActionBarAlpha;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mActionBarBackgroundDrawable = getResources().getDrawable(R.drawable.nav_bar_background);
		mActionBarBackgroundDrawable.setAlpha(0);

		getActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Get the section name array for Navigation Drawer
		sectionNames = getResources().getStringArray(R.array.section_names);

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
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

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

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

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
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Swaps fragments in the main content view
	 * 
	 * OBS
	 * 
	 * Will be done dynamically, add the rest of the fragments
	 * */
	private void selectItem(int position) {

		FragmentManager fragmentManager = getFragmentManager();

		if (position == 0) {
			// Create a new fragment and specify the planet to show based on
			// position
			Fragment fragmentIndex = new IndexFragment();

			// Insert the fragment by replacing any existing fragment
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentIndex).commit();
		}

		if (position == 1) {
			// Create a new fragment and specify the planet to show based on
			// position
			Fragment fragmentExercise = new ExerciseFragment();

			// Insert the fragment by replacing any existing fragment
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentExercise).commit();
		}

		/*
		 * Need to implement a SettingsFragment
		 */
		if (position == 2) {
			// Create a new fragment and specify the planet to show based on
			// position
			Fragment fragmentSettings = new PrefsFragment();

			// Insert the fragment by replacing any existing fragment
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentSettings).commit();
		}

		if (position == 3) {
			// Create a new fragment and specify the planet to show based on
			// position
			Fragment fragmentAbout = new AboutFragment();

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

	public void setNavigationBarBackgroundTransparency(AbsListView view, int listHeaderHeight, int firstVisibleItem) {

		// Get the first visible child
		View child = view.getChildAt(firstVisibleItem);

		final int headerHeight = listHeaderHeight - getActionBar().getHeight();
		final float ratio = (float) Math.min(Math.max(Math.abs(child.getTop()) + child.getHeight() * firstVisibleItem, 0), headerHeight) / headerHeight;
		final int newAlpha = (int) (ratio * 255);
		mActionBarBackgroundDrawable.setAlpha(newAlpha);

		mActionBarAlpha = newAlpha;

	}

}
