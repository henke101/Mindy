package se.chalmers.mindy.fragment;

import java.util.ArrayList;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.DiaryAdapter;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import se.chalmers.mindy.util.Tools;
import android.app.Activity;
import android.app.ListFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DiaryListFragment extends ListFragment implements OnScrollListener {

	private static final int INSERT_ID = Menu.FIRST;
	// private static final int DELETE_ID = Menu.FIRST + 1;

	private MainActivity mActivity;
	private MindyDatabaseAdapter mDbHelper;
	private View mListHeader;
	private ArrayList<Integer> itemIds;

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = mActivity.getLayoutInflater().inflate(R.layout.fragment_diary_list, container, false);
		ListView listView = (ListView) v.findViewById(android.R.id.list);
		listView.setOnScrollListener(this);
		listView.setDividerHeight(0);

		Typeface robotoLightCondensed = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_condensed_light.ttf");

		mDbHelper = new MindyDatabaseAdapter(getActivity());
		mDbHelper.open();

		fillData();

		mListHeader = inflater.inflate(R.layout.list_header, null);

		TextView titleView = (TextView) mListHeader.findViewById(R.id.header_title);
		titleView.setText(R.string.diary);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));

		ImageView imageView = (ImageView) mListHeader.findViewById(R.id.header_background);
		Tools.setTwoStepBitmapBackground(mActivity, R.drawable.chain, imageView);

		listView.addHeaderView(mListHeader);

		View addEntryHeader = inflater.inflate(R.layout.diary_list_header, null);

		Button createNote = (Button) addEntryHeader.findViewById(R.id.create_note_button);
		createNote.setTypeface(robotoLightCondensed);
		createNote.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				createNote();
			}
		});
		listView.addHeaderView(addEntryHeader);

		return v;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mDbHelper.close();
		setListAdapter(null);
	}

	private void fillData() {
		itemIds = mDbHelper.fetchAllNoteIds();
		setListAdapter(new DiaryAdapter(mActivity, mDbHelper.fetchAllNotes()));
	}

	private void createNote() {
		mActivity.pushFragment(new DiaryEditFragment());
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		DiaryEditFragment fragment = new DiaryEditFragment();

		Bundle bundle = new Bundle();

		bundle.putLong("rowID", itemIds.get((int) id));
		fragment.setArguments(bundle);
		mActivity.pushFragment(fragment);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if (visibleItemCount > firstVisibleItem) {
			mActivity.setActionBarTransparencyFromListViewPosition(view, mListHeader.getHeight());
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Do nothing
	}
}