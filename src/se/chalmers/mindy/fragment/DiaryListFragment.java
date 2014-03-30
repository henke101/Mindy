package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DiaryListFragment extends ListFragment {

	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;

	//private static final int INSERT_ID = Menu.FIRST;
	private static final int DELETE_ID = Menu.FIRST + 1;

	private DiaryDbAdapter mDbHelper;

	/** Called when the activity is first created. */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.diary_list, container, false);
		mDbHelper = new DiaryDbAdapter(getActivity());
		mDbHelper.open();
		fillData();
		return v;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
//		final ListView diaryList = getListView();
//		diaryList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//        registerForContextMenu(diaryList);
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

	public void createNote() {
		Intent i = new Intent(getActivity(), DiaryEditFragment.class);
		startActivityForResult(i, ACTIVITY_CREATE);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(getActivity(), DiaryEditFragment.class);
		i.putExtra(DiaryDbAdapter.KEY_ROWID, id);
		startActivityForResult(i, ACTIVITY_EDIT);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == Activity.RESULT_OK) {
			fillData();
		}
	}
}