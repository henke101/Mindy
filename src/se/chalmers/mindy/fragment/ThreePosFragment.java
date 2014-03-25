package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.core.ThreePosAdapter;
import se.chalmers.mindy.core.ThreePosItem;
import se.chalmers.mindy.util.ExpanderAnimation;
import android.app.Activity;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ThreePosFragment extends ListFragment {

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;
	TextView expanded;

	ThreePosItem[] threePosItemList = { new ThreePosItem("En jäkligt bra sak", "Jodå, jag lovar", "Just precis!"), new ThreePosItem("En Bra Sak", "En Annan Bra Sak", "Och Ytterliggare En Bra Sak") };

	public ThreePosFragment() {

	}

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = sharedPrefs.edit();

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ListView listView = getListView();

		LayoutInflater inflater = mActivity.getLayoutInflater();

		View headerView = inflater.inflate(R.layout.fragment_threepositive, null);
		TextView titleView = (TextView) headerView.findViewById(R.id.pos_title);
		titleView.setText(R.string.app_name);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_light.ttf"));

		listView.addHeaderView(headerView);

		// Initialize and add text input header.
		View addItemHeader = inflater.inflate(R.layout.three_pos_input_header, null);
		Button addButton = (Button) addItemHeader.findViewById(R.id.add_threepos_button);
		final LinearLayout inputContainer = (LinearLayout) addItemHeader.findViewById(R.id.input_container);

		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ExpanderAnimation expanderAnim = new ExpanderAnimation(inputContainer, 500);
				inputContainer.startAnimation(expanderAnim);
			}
		});
		listView.addHeaderView(addItemHeader);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Log.d("List item clicked", "Item id: " + id);

			}
		});
		//
		// EditText editText = (EditText) view.findViewById(R.id.positive_one_input);
		// editText.setOnEditorActionListener(new OnEditorActionListener() {
		// @Override
		// public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// boolean handled = false;
		// if (actionId == EditorInfo.IME_ACTION_SEND) {
		// sendMessage();
		// handled = true;
		// }
		// return handled;
		// }
		// });

		ThreePosAdapter adapter = new ThreePosAdapter(mActivity.getLayoutInflater().getContext(), R.layout.three_positive_item, threePosItemList);
		setListAdapter(adapter);
		System.out.println("onactivitycreated works");

	}

}
