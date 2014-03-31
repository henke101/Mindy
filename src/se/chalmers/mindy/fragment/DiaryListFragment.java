package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DiaryListFragment extends ListFragment {

	private static final int INSERT_ID = Menu.FIRST;
	//private static final int DELETE_ID = Menu.FIRST + 1;

	private DiaryDbAdapter mDbHelper;
	private DiaryEditFragment fragmentDiary = new DiaryEditFragment();

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.diary_list, container, false);
		setHasOptionsMenu(true);
		mDbHelper = new DiaryDbAdapter(getActivity());
		mDbHelper.open();
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		fillData();
	}

	private void fillData() {
		Cursor c = mDbHelper.fetchAllNotes();
		getActivity().startManagingCursor(c);

		// Create an array to specify the fields we want to display in the list (only TITLE)
		String[] from = new String[]{DiaryDbAdapter.KEY_TITLE};

		// and an array of the fields we want to bind those fields to (in this case just text1)
		int[] to = new int[]{R.id.text1};


		// Now create a simple cursor adapter and set it to display
		SimpleCursorAdapter notes = 
				new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.diary_row, c, from, to);
		setListAdapter(notes);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    super.onCreateOptionsMenu(menu,inflater);
	    menu.add(0, INSERT_ID, 0, R.string.diary_menu_insert);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()) {
		case INSERT_ID:
			createNote();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void createNote() {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentDiary).commit();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == Activity.RESULT_OK) {
			fillData();
		}
	}
}