package se.chalmers.mindy.fragment;

import java.util.ArrayList;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.IndexAdapter;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.util.Tools;
import se.chalmers.mindy.view.IndexListItem;
import se.chalmers.mindy.view.SoundIndexListItem;
import se.chalmers.mindy.view.ThreePositiveIndexItem;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class IndexFragment extends Fragment implements OnScrollListener {
	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;

	private ListView mListView;
	public View mListHeader;
	private ArrayList<IndexListItem> dummyItems;

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = sharedPrefs.edit();

		mListHeader = mActivity.getLayoutInflater().inflate(R.layout.list_header, null);

		ImageView imageView = (ImageView) mListHeader.findViewById(R.id.header_background);
		Tools.setTwoStepBitmapBackground(mActivity, R.drawable.ice, imageView);

		TextView titleView = (TextView) mListHeader.findViewById(R.id.header_title);
		titleView.setText(R.string.title_fragment_index);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View root = inflater.inflate(R.layout.fragment_index, null);

		mListView = (ListView) root.findViewById(R.id.index_list);
		mListView.setDividerHeight(0);
		mListView.setBackgroundColor(mActivity.getResources().getColor(R.color.bg_color_grey));
		mListView.setHeaderDividersEnabled(true);

		mListView.addHeaderView(mListHeader);

		// Dummy items
		dummyItems = new ArrayList<IndexListItem>();

		dummyItems.add(new ThreePositiveIndexItem(mActivity, new ThreePosFragment()));
		dummyItems.add(new SoundIndexListItem(mActivity, R.string.sleeping_pill, R.string.short_desc_sleeping_pill, R.raw.sleeping_pill,
				new AudioExerciseFragment()));
		dummyItems.add(new SoundIndexListItem(mActivity, R.string.body_focus, R.string.short_desc_body_focus, R.raw.body_focus2, new AudioExerciseFragment()));

		IndexAdapter adapter = new IndexAdapter(mActivity, dummyItems);

		mListView.setAdapter(adapter);

		return root;

	}

	@Override
	public void onResume() {
		super.onResume();
		mListView.setOnScrollListener(this);
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
