package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.IndexAdapter;
import se.chalmers.mindy.core.IndexItem;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.core.SoundIndexItem;
import se.chalmers.mindy.util.Tools;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
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

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = sharedPrefs.edit();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View root = inflater.inflate(R.layout.fragment_index, null);

		mListView = (ListView) root.findViewById(R.id.index_list);
		mListView.setDividerHeight(0);
		mListView.setBackgroundColor(Color.rgb(226, 226, 226));
		mListView.setHeaderDividersEnabled(true);

		final View headerView = mActivity.getLayoutInflater().inflate(R.layout.list_header, null);

		ImageView imageView = (ImageView) headerView.findViewById(R.id.header_background);
		Tools.setTwoStepBitmapBackground(mActivity, R.drawable.fluff, imageView);

		TextView titleView = (TextView) headerView.findViewById(R.id.header_title);
		titleView.setText(R.string.title_fragment_index);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));

		mListHeader = headerView;
		mListView.addHeaderView(headerView);

		// Dummy items
		IndexItem[] items = {
				new SoundIndexItem(mActivity, "Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig", R.raw.sample_soundfile),
				new SoundIndexItem(mActivity, "Kroppsscanning",
						"Kroppscanning kan vara vŠldigt behagligt och dŠrfšr somnar mŒnga av švningen. Och mŒnga beskriver hur de sover djupare, Šven efter mŒnga Œrs sšmnproblem.",
						R.raw.sample_soundfile),
				new SoundIndexItem(mActivity, "Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig", R.raw.sample_soundfile),
				new SoundIndexItem(mActivity, "Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig", R.raw.sample_soundfile) };
		IndexAdapter adapter = new IndexAdapter(mActivity, items);

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
			mActivity.setNavigationBarBackgroundTransparency(view, mListHeader.getHeight(), firstVisibleItem);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Do nothing
	}

}
