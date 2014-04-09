package se.chalmers.mindy.fragment;

import java.util.Calendar;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import se.chalmers.mindy.view.DiaryItem;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DiaryEditFragment extends Fragment {

	private EditText mTitleText;
	private EditText mBodyText;
	private Long mRowId;
	private MindyDatabaseAdapter mDbHelper;
	private MainActivity mActivity;

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity;
		mActivity.setActionBarBackgroundTransparency(255);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_diary_edit, container, false);

		Typeface robotoThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_thin.ttf");
		Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_light.ttf");
		Typeface robotoCondensedLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_condensed_light.ttf");
		container.removeAllViews();

		mDbHelper = new MindyDatabaseAdapter(getActivity());
		mDbHelper = mDbHelper.open();

		mTitleText = (EditText) v.findViewById(R.id.diary_title);
		mTitleText.setTypeface(robotoLight);
		mBodyText = (EditText) v.findViewById(R.id.diary_body);
		mBodyText.setTypeface(robotoLight);

		Button confirmButton = (Button) v.findViewById(R.id.diary_confirm_button);
		confirmButton.setTypeface(robotoCondensedLight);
		Button cancelButton = (Button) v.findViewById(R.id.diary_cancel_button);
		cancelButton.setTypeface(robotoCondensedLight);
		Button deleteButton = (Button) v.findViewById(R.id.diary_delete_button);
		deleteButton.setTypeface(robotoCondensedLight);

		TextView titleLabel = (TextView) v.findViewById(R.id.diary_title_label);
		titleLabel.setTypeface(robotoThin);
		TextView bodyLabel = (TextView) v.findViewById(R.id.diary_body_label);
		bodyLabel.setTypeface(robotoThin);

		Bundle bundle = getArguments();
		if (bundle != null) {
			mRowId = bundle.getLong("rowID");
			deleteButton.setVisibility(View.VISIBLE);
		}

		deleteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				boolean success = mDbHelper.deleteNote(mRowId);
				FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.content_frame, new DiaryListFragment()).commit();
			}
		});
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mTitleText.getText().length() > 0 && mBodyText.getText().length() > 0) {
					saveState();
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.content_frame, new DiaryListFragment()).commit();
				} else {
					Toast.makeText(mActivity, R.string.diary_empty_fields, Toast.LENGTH_LONG).show();
				}
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
			DiaryItem note = mDbHelper.fetchNote(mRowId);
			mTitleText.setText(note.getTitle());
			mBodyText.setText(note.getBody());
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
	public void onDestroy() {
		super.onDestroy();
		mDbHelper.close();
	}

	private void saveState() {
		DiaryItem item = new DiaryItem(mTitleText.getText().toString(), mBodyText.getText().toString(), Calendar.getInstance());

		if (mRowId == null) {
			long id = mDbHelper.createNote(item);
			if (id > 0) {
				mRowId = id;
			}
		} else {
			mDbHelper.updateNote(mRowId, item);
		}
	}
}
