package se.chalmers.mindy.fragment;

import java.util.ArrayList;
import java.util.Calendar;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.core.ThreePosAdapter;
import se.chalmers.mindy.core.ThreePosItem;
import se.chalmers.mindy.util.ExpandAnimation;
import se.chalmers.mindy.util.Tools;
import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ThreePosFragment extends ListFragment implements OnScrollListener {

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;
	TextView expanded;
	String stringInputOne;
	String stringInputTwo;
	String stringInputThree;
	Calendar cDate;
	ArrayList<ThreePosItem> threePosItemList = new ArrayList<ThreePosItem>();
	EditText inputOne;
	EditText inputTwo;
	EditText inputThree;
	Button addButton;
	RadioGroup dateRadioGroup;
	LinearLayout inputContainer;
	private View mListHeader;

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
		listView.setBackgroundColor(getResources().getColor(R.color.bg_color_grey));
		listView.setDividerHeight(0);
		listView.setOnScrollListener(this);

		// = mActivity.getLayoutInflater().inflate(R.layout., null);

		mListHeader = inflater.inflate(R.layout.list_header, null);

		TextView titleView = (TextView) mListHeader.findViewById(R.id.header_title);
		titleView.setText(R.string.three_pos);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));
		titleView.setTextSize(30);

		ImageView imageView = (ImageView) mListHeader.findViewById(R.id.header_background);
		Tools.setTwoStepBitmapBackground(mActivity, R.drawable.fluff, imageView);

		listView.addHeaderView(mListHeader);

		// Initialize and add text input header.
		View addItemHeader = inflater.inflate(R.layout.three_pos_input_header, null);
		final Button okButton = (Button) addItemHeader.findViewById(R.id.ok_button);

		TextView positiveTextLabel = (TextView) addItemHeader.findViewById(R.id.three_positive_text);

		inputContainer = (LinearLayout) addItemHeader.findViewById(R.id.input_container);

		dateRadioGroup = (RadioGroup) addItemHeader.findViewById(R.id.date_buttons);
		addButton = (Button) addItemHeader.findViewById(R.id.add_threepos_button);

		inputOne = (EditText) addItemHeader.findViewById(R.id.positive_one_input);
		inputTwo = (EditText) addItemHeader.findViewById(R.id.positive_two_input);
		inputThree = (EditText) addItemHeader.findViewById(R.id.positive_three_input);

		/**
		 * Setting the fonts for the inputs
		 */
		Typeface robotoConLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_condensed_light.ttf");
		Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_light.ttf");

		positiveTextLabel.setTypeface(robotoLight);
		inputOne.setTypeface(robotoLight);
		inputTwo.setTypeface(robotoLight);
		inputThree.setTypeface(robotoLight);
		addButton.setTypeface(robotoConLight);
		okButton.setTypeface(robotoConLight);

		threePosItemList = mActivity.getMindyDb().fetchAllPositives();

		listView.addHeaderView(addItemHeader);

		ThreePosAdapter adapter = new ThreePosAdapter(mActivity.getLayoutInflater().getContext(), R.layout.three_positive_item, threePosItemList);
		setListAdapter(adapter);

		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ExpandAnimation expandAnim = new ExpandAnimation(inputContainer, 500);
				inputContainer.startAnimation(expandAnim);
				InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);

				if (addButton.getText().equals(getString(R.string.button_abort))) {
					addButton.setText(R.string.button_add_new);
					imm.hideSoftInputFromWindow(inputOne.getWindowToken(), 0);

				} else {
					addButton.setText(R.string.button_abort);
					inputOne.requestFocus();
					imm.showSoftInput(inputOne, 0);
				}
			}
		});
		dateRadioGroup.check(R.id.today_button);

		OnEditorActionListener createListener = new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(final TextView textView, final int id, final KeyEvent keyEvent) {
				if (id == R.id.create || id == EditorInfo.IME_ACTION_DONE) {

					createNewCard();

					ExpandAnimation expandAnim = new ExpandAnimation(inputContainer, 500);
					inputContainer.startAnimation(expandAnim);

					return true;
				}
				return false;

			}
		};

		inputThree.setOnEditorActionListener(createListener);

		okButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				createNewCard();

				ExpandAnimation expandAnim = new ExpandAnimation(inputContainer, 500);
				inputContainer.startAnimation(expandAnim);

			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Log.d("List item clicked", "Item id: " + id);
			}
		});

	}

	private void createNewCard() {
		cDate = Calendar.getInstance();
		if (dateRadioGroup.getCheckedRadioButtonId() == R.id.yesterday_button) {
			cDate.add(Calendar.DATE, -1);
		}

		stringInputOne = inputOne.getText().toString();
		stringInputTwo = inputTwo.getText().toString();
		stringInputThree = inputThree.getText().toString();

		ThreePosItem newPosItem = new ThreePosItem(cDate, stringInputOne, stringInputTwo, stringInputThree);
		mActivity.getMindyDb().insertNewThreePositive(newPosItem);
		threePosItemList = mActivity.getMindyDb().fetchAllPositives();

		InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(inputThree.getWindowToken(), 0);

		inputOne.setText(null);
		inputTwo.setText(null);
		inputThree.setText(null);

		inputOne.setHint(R.string.add_positive_one);
		inputTwo.setHint(R.string.add_positive_two);
		inputThree.setHint(R.string.add_positive_three);

		addButton.setText(R.string.button_add_new);

		dateRadioGroup.check(R.id.today_button);

		ThreePosAdapter adapter = new ThreePosAdapter(mActivity.getLayoutInflater().getContext(), R.layout.three_positive_item, threePosItemList);
		setListAdapter(adapter);
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
