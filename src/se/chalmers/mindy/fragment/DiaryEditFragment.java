package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.content.ContentValues;
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

	public DiaryEditFragment(){

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.diary_edit, container, false);

		mTitleText = (EditText) v.findViewById(R.id.diary_title);
		mBodyText = (EditText) v.findViewById(R.id.diary_body);
		Button confirmButton = (Button) v.findViewById(R.id.diary_confirm);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				saveNote();
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
	private void saveNote() {
		// save/update the note
		ContentValues values = new ContentValues(2);
		values.put(DiaryDbAdapter.KEY_TITLE, mTitleText.getText().toString());
		values.put(DiaryDbAdapter.KEY_BODY, mBodyText.getText().toString());
		if (mCurrentNote != null) {
			getActivity().getContentResolver().update(mCurrentNote, values, null, null);
		}
		Toast.makeText(getActivity(), "Sparad", Toast.LENGTH_SHORT).show();
	}
}
