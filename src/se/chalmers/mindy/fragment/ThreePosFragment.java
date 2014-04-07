package se.chalmers.mindy.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.core.ThreePosAdapter;
import se.chalmers.mindy.core.ThreePosItem;
import se.chalmers.mindy.util.ExpandAnimation;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ThreePosFragment extends ListFragment {

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
	RadioGroup dateButtons;
	LinearLayout inputContainer;

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
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_condensed_light.ttf"));

		listView.addHeaderView(headerView);

		// Initialize and add text input header.
		View addItemHeader = inflater.inflate(R.layout.three_pos_input_header, null);
		final Button okButton = (Button) addItemHeader.findViewById(R.id.ok_button);

		TextView positiveTextLabel = (TextView) addItemHeader.findViewById(R.id.three_positive_text);

		inputContainer = (LinearLayout) addItemHeader.findViewById(R.id.input_container);
		dateButtons = (RadioGroup) addItemHeader.findViewById(R.id.date_buttons);
		addButton = (Button) addItemHeader.findViewById(R.id.add_threepos_button);	
		inputOne = (EditText) addItemHeader.findViewById(R.id.positive_one_input);
		inputTwo = (EditText) addItemHeader.findViewById(R.id.positive_two_input);
		inputThree  = (EditText) addItemHeader.findViewById(R.id.positive_three_input);
		

		/**
		 * Setting the fonts for the inputs
		 */
		Typeface robotoConLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_condensed_light.ttf");
		Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_light.ttf");
		
		positiveTextLabel.setTypeface(robotoLight);
		inputOne.setTypeface(robotoConLight);
		inputTwo.setTypeface(robotoConLight);
		inputThree.setTypeface(robotoConLight);
		addButton.setTypeface(robotoConLight);
		okButton.setTypeface(robotoConLight);

		threePosItemList = mActivity.getMindyDb().fetchAllPositives();
		
		Collections.reverse(threePosItemList);
		ThreePosAdapter adapter = new ThreePosAdapter(mActivity.getLayoutInflater().getContext(), R.layout.three_positive_item, threePosItemList);
		setListAdapter(adapter);

		listView.addHeaderView(addItemHeader);

		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ExpandAnimation expandAnim = new ExpandAnimation(inputContainer, 500);
				inputContainer.startAnimation(expandAnim);
				InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(
					      Context.INPUT_METHOD_SERVICE);

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
		
		dateButtons.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.yesterday_button) {
					cDate = Calendar.getInstance();
					cDate.add(Calendar.DATE, -1);					
				}
				else {
					cDate = Calendar.getInstance();
				}
				
			}
			
		});

		OnEditorActionListener createListener = new OnEditorActionListener(){
			
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
			if (cDate == null) {
				cDate = Calendar.getInstance();
			}
			stringInputOne = inputOne.getText().toString();
			stringInputTwo =inputTwo.getText().toString();
			stringInputThree = inputThree.getText().toString();
			
			ThreePosItem newPosItem = new ThreePosItem(cDate, stringInputOne, stringInputTwo, stringInputThree);
			mActivity.getMindyDb().insertNewThreePositive(newPosItem);
			threePosItemList = mActivity.getMindyDb().fetchAllPositives();
			
			InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(
				      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(inputThree.getWindowToken(), 0);
			
			inputOne.setText(null);
			inputTwo.setText(null);
			inputThree.setText(null);
			
			inputOne.setHint(R.string.add_positive_one);
			inputTwo.setHint(R.string.add_positive_two);
			inputThree.setHint(R.string.add_positive_three);
			
			addButton.setText(R.string.button_add_new);
			
			dateButtons.check(R.id.today_button);
			
			Collections.reverse(threePosItemList);
			ThreePosAdapter adapter = new ThreePosAdapter(mActivity.getLayoutInflater().getContext(), R.layout.three_positive_item, threePosItemList);
			setListAdapter(adapter);
	 }
}
