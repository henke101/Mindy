package se.chalmers.mindy.core;

import se.chalmers.mindy.R;
import se.chalmers.mindy.fragment.AboutFragment;
import se.chalmers.mindy.fragment.ExerciseFragment;
import se.chalmers.mindy.fragment.IndexFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private CharSequence mTitle;

	private String[] sectionNames;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Get the section name array for Navigation Drawer
		sectionNames = getResources().getStringArray(R.array.section_names);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, sectionNames));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				selectItem(position);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Swaps fragments in the main content view
	 * 
	 * 
	 * OBS
	 * 
	 * Skall göras dynamisk, nu hämtas alltid samma fragment L‰gg till de andra
	 * sidorna!
	 * */
	private void selectItem(int position) {
		// Print out which position
		Log.d(ACTIVITY_SERVICE, "" + position);

		if (position == 0) {
			// Create a new fragment and specify the planet to show based on
			// position
			Fragment fragmentIndex = new IndexFragment();

			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.content_frame, fragmentIndex).commit();
		}
		
		if(position==1){
		// Create a new fragment and specify the planet to show based on
		// position
		Fragment fragmentExercise = new ExerciseFragment();
		
		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame,
		fragmentExercise).commit();
		}

		/*
		 * Need to implement a SettingsFragment
		 */
		// if(position==2){
		// // Create a new fragment and specify the planet to show based on
		// // position
		// Fragment fragmentSettings = new SettingsFragment();
		//
		// // Insert the fragment by replacing any existing fragment
		// FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager.beginTransaction().replace(R.id.content_frame,
		// fragmentSettings).commit();
		// }

		if (position == 3) {
			// Create a new fragment and specify the planet to show based on
			// position
			Fragment fragmentAbout = new AboutFragment();
			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
			.replace(R.id.content_frame, fragmentAbout).commit();
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
}
