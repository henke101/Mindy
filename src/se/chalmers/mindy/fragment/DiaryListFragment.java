package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import android.R.color;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class DiaryListFragment extends ListFragment{

	private static final int INSERT_ID = Menu.FIRST;
	//private static final int DELETE_ID = Menu.FIRST + 1;

	private MindyDatabaseAdapter mDbHelper;
	private SimpleCursorAdapter mListAdapter;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.diary_list, container, false);
		setHasOptionsMenu(true);
		
		View rowView = inflater.inflate(R.layout.diary_row, null);
		Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_light.ttf");
		TextView noteRow = (TextView) rowView.findViewById(R.id.row);
		
		noteRow.setTypeface(robotoLight);
		noteRow.setTextColor(color.white);
		Log.d("listfragment", ""+ noteRow.getTextSize() + " ,"+noteRow.toString());

		mDbHelper = new MindyDatabaseAdapter(this.getActivity());
		mDbHelper.open();

		fillData();

		Button createNote = (Button) v.findViewById(R.id.create_note_button);
		createNote.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				createNote();	
			}
		});
		return v;
	}
	@Override 
	public void onDestroy(){
		super.onDestroy();
		mDbHelper.close();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		registerForContextMenu(getListView());
	}

	private void fillData() {
		Cursor mCursor = mDbHelper.fetchAllNotes();
		// Create an array to specify the fields we want to display in the list
		String[] from = new String[]{MindyDatabaseAdapter.KEY_TITLE};

		// and an array of the fields we want to bind those fields to
		int[] to = new int[]{R.id.row};

		// Now create a simple cursor adapter and set it to display 
		mListAdapter = 
				new SimpleCursorAdapter(this.getActivity(), R.layout.diary_row, mCursor, from, to);
		setListAdapter(mListAdapter);
		//mCursor.close();
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
		fragmentManager.beginTransaction().replace(R.id.content_frame, new DiaryEditFragment()).commit();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		FragmentManager fragmentManager = getFragmentManager();
		DiaryEditFragment fragment = new DiaryEditFragment();
		Bundle bundle = new Bundle();
		bundle.putLong("rowID", id );
		fragment.setArguments(bundle);
		fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
		Log.i("Hello!", "Clicked! YAY!");
		}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (resultCode == Activity.RESULT_OK) {
		}
	}
}