package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DiaryEditFragment extends Fragment {

	private EditText mTitleText;
	private EditText mBodyText;
	private Uri mCurrentNote;
	
	public static final String AUTHORITY = "com.example.android.honeypad.notesprovider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/notes");

	public DiaryEditFragment(){

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.diary_edit, container, false);

		mTitleText = (EditText) v.findViewById(R.id.title);
		mBodyText = (EditText) v.findViewById(R.id.body);
		Button confirmButton = (Button) v.findViewById(R.id.confirm);
		confirmButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
			}
		});

		if (savedInstanceState != null
				&& savedInstanceState.containsKey(DiaryDbAdapter.KEY_ID)) {
			mCurrentNote = Uri.parse(savedInstanceState.getString(DiaryDbAdapter.KEY_ID));
		}
		populateFields();
		return v;
	}
	
	private void populateFields() {
		
		if (mCurrentNote != null) {
			Cursor c = null;
			try {
				c = getActivity().getContentResolver().query(mCurrentNote, null, null, null,
						null);
				if (c.moveToFirst()) {
					mTitleText.setText(c.getString(DiaryDbAdapter.TITLE_COLUMN));
					mBodyText.setText(c.getString(DiaryDbAdapter.BODY_COLUMN));
				}
			} finally {
				if (c != null) {
					c.close();
				}
			}
		}
	}
//	private void saveNote() {
//		// save/update the note
//		ContentValues values = new ContentValues(2);
//		values.put(DiaryDbAdapter.KEY_TITLE, mTitleText.getText().toString());
//		values.put(DiaryDbAdapter.KEY_BODY, mBodyText.getText().toString());
//		if (mCurrentNote != null) {
//			getActivity().getContentResolver().update(mCurrentNote, values, null, null);
//		} else {
//			getActivity().getContentResolver().insert(DiaryDbAdapter.CONTENT_URI, values);
//		}
//		Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
//	}
}
