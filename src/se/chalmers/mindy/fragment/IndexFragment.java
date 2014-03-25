package se.chalmers.mindy.fragment;

import java.util.ArrayList;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.IndexAdapter;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.pojo.IndexItem;
import se.chalmers.mindy.pojo.SoundIndexItem;
import se.chalmers.mindy.util.Tools;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class IndexFragment extends Fragment implements OnScrollListener, OnItemClickListener, OnItemLongClickListener {
	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;

	private ListView mListView;
	public View mListHeader;
	private ArrayList<IndexItem> dummyItems;

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
		mListView.setOnItemClickListener(this);
		mListView.setOnItemLongClickListener(this);

		final View headerView = mActivity.getLayoutInflater().inflate(R.layout.list_header, null);

		ImageView imageView = (ImageView) headerView.findViewById(R.id.header_background);
		Tools.setTwoStepBitmapBackground(mActivity, R.drawable.fluff, imageView);

		TextView titleView = (TextView) headerView.findViewById(R.id.header_title);
		titleView.setText(R.string.title_fragment_index);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));

		mListHeader = headerView;
		mListView.addHeaderView(headerView);

		// Dummy items
		dummyItems = new ArrayList<IndexItem>();
		dummyItems.add(new SoundIndexItem(mActivity, "1 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "2 Kroppsscanning",
				"Kroppscanning kan vara vŠldigt behagligt och dŠrfšr somnar mŒnga av švningen. Och mŒnga beskriver hur de sover djupare, Šven efter mŒnga Œrs sšmnproblem.", R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "3 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "4 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "5 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "6 Urban Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "7 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "8 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "9 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "10 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "11 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "12 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));
		dummyItems.add(new SoundIndexItem(mActivity, "13 Sšmnpiller", "Perfekt fšr dig som har svŒrt att sova pŒ kvŠllarna, prova švningen liggandes i sŠngen nŠr du gŒtt och lagt dig",
				R.raw.sample_soundfile));

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
			mActivity.setNavigationBarBackgroundTransparency(view, mListHeader.getHeight(), firstVisibleItem);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// Do nothing
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int id, long pos) {
		Log.d("id: " + id, "pos: " + pos);
		IndexAdapter adapter = (IndexAdapter) ((HeaderViewListAdapter) parent.getAdapter()).getWrappedAdapter();

		adapter.remove((int) pos);
		adapter.notifyDataSetChanged();

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int id, long pos) {

		return true;
	}
}
