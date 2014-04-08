package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DiaryEditFragment extends Fragment {

	private EditText mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private MindyDatabaseAdapter mDbHelper;

	public DiaryEditFragment(){

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.diary_edit, container, false);
		
		container.removeAllViews();
		
		mDbHelper = new MindyDatabaseAdapter(getActivity());
		mDbHelper = mDbHelper.open();
		
		mTitleText = (EditText) v.findViewById(R.id.diary_title);
		mBodyText = (EditText) v.findViewById(R.id.diary_body);
		
		Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_light.ttf");
				
		Button confirmButton = (Button) v.findViewById(R.id.diary_confirm);
		Button cancelButton = (Button) v.findViewById(R.id.new_note_cancel);
		Button deleteButton = (Button) v.findViewById(R.id.delete_note);
		cancelButton.setTypeface(robotoLight);
		confirmButton.setTypeface(robotoLight);
		deleteButton.setTypeface(robotoLight);
		
		Bundle bundle = getArguments();
		if (bundle != null){
		mRowId = bundle.getLong("rowID");
		deleteButton.setVisibility(View.VISIBLE);
		}

		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDbHelper.deleteNote(mRowId);
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, new DiaryListFragment()).commit();
			}
		});
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				saveState();
				//Toast.makeText(getActivity(), "Sparat", Toast.LENGTH_SHORT).show();;
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, new DiaryListFragment()).commit();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, new DiaryListFragment()).commit();
			}
		});

		populateFields();
		return v;
	}

	private void populateFields() {
		if (mRowId != null) {
			Cursor note = mDbHelper.fetchNote(mRowId);
			mTitleText.setText(note.getString(
					note.getColumnIndexOrThrow(MindyDatabaseAdapter.KEY_TITLE)));
			mBodyText.setText(note.getString(
					note.getColumnIndexOrThrow(MindyDatabaseAdapter.KEY_BODY)));
			//note.close();
		}
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		saveState();
		outState.putSerializable(MindyDatabaseAdapter.KEY_ROWID, mRowId);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		populateFields();
	}
	
	@Override 
	public void onDestroy(){
		super.onDestroy();
		mDbHelper.close();
	}
	
	private void saveState() {
		String title = mTitleText.getText().toString();
		String body = mBodyText.getText().toString();
		
		if (mRowId == null) {
			long id = mDbHelper.createNote(title, body);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateNote(mRowId, title, body);
		}
	}
}
