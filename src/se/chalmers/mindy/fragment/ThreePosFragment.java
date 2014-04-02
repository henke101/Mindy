package se.chalmers.mindy.fragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

public class ThreePosFragment extends ListFragment {

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;
	TextView expanded;
	String stringInputOne;
	String stringInputTwo;
	String stringInputThree;
	Calendar calendar;
	SimpleDateFormat dfDate = new SimpleDateFormat("yyyy-MM-dd");
	String date;

	ArrayList<ThreePosItem> threePosItemList = new ArrayList<ThreePosItem>();
	//	ThreePosItem[] threePosItemList = { new ThreePosItem("En j�kligt bra sak", "Jod�, jag lovar", "Just precis!"), new ThreePosItem("En Bra Sak", "En Annan Bra Sak", "Och Ytterliggare En Bra Sak") };

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
		final Button addButton = (Button) addItemHeader.findViewById(R.id.add_threepos_button);
		final RadioGroup dateButtons = (RadioGroup) addItemHeader.findViewById(R.id.date_buttons); 
		final LinearLayout inputContainer = (LinearLayout) addItemHeader.findViewById(R.id.input_container);
		final EditText inputOne = (EditText) addItemHeader.findViewById(R.id.positive_one_input);
		final EditText inputTwo = (EditText) addItemHeader.findViewById(R.id.positive_two_input);
		final EditText inputThree = (EditText) addItemHeader.findViewById(R.id.positive_three_input);
		final Button okButton = (Button) addItemHeader.findViewById(R.id.ok_button);
		
		OnEditorActionListener createListener = new OnEditorActionListener(){
			
			@Override
			public boolean onEditorAction(final TextView textView, final int id, final KeyEvent keyEvent) {
				if (id == R.id.create || id == EditorInfo.IME_ACTION_DONE) {
					stringInputOne = inputOne.getText().toString();
					stringInputTwo =inputTwo.getText().toString();
					stringInputThree = inputThree.getText().toString();
					threePosItemList.add(new ThreePosItem(stringInputOne, stringInputTwo, stringInputThree));
					
					InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(
						      Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
					
					ExpandAnimation expandAnim = new ExpandAnimation(inputContainer, 500);
					inputContainer.startAnimation(expandAnim);
					
					inputOne.setText(null);
					inputTwo.setText(null);
					inputThree.setText(null);
					
					inputOne.setHint(R.string.add_positive_one);
					inputTwo.setHint(R.string.add_positive_two);
					inputThree.setHint(R.string.add_positive_three);
					
					addButton.setText("L�gg till ny");
					
					return true;
				}
				return false;
			
			}
		};

		inputThree.setOnEditorActionListener(createListener);

		addButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ExpandAnimation expandAnim = new ExpandAnimation(inputContainer, 500);
				inputContainer.startAnimation(expandAnim);
				InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(
					      Context.INPUT_METHOD_SERVICE);

				if (addButton.getText().equals("Avbryt")) {
					addButton.setText("L�gg till ny");
					imm.hideSoftInputFromWindow(inputOne.getWindowToken(), 0);
				} else {
					addButton.setText("Avbryt");
					inputOne.requestFocus();
					imm.showSoftInput(inputOne, 0);
				}
			}
		});

		dateButtons.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (checkedId == R.id.yesterday_button) {
					calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, -1);
					date = dfDate.format(calendar.getTime());
				}
				else {
					calendar = Calendar.getInstance();
					date = dfDate.format(calendar.getTime());
				}
			}
			
		});

		
		listView.addHeaderView(addItemHeader);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Log.d("List item clicked", "Item id: " + id);
			}
		});
		/**
		 * okButton saves all the input values into a card in the ThreePosItemList
		 * Problem right now, the strings arent being refreshed. It displays old strings and in a strange order
		 */
//		okButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				stringInputOne = inputOne.getText().toString();
//				stringInputTwo =inputTwo.getText().toString();
//				stringInputThree = inputThree.getText().toString();
//				threePosItemList.add(0, new ThreePosItem(stringInputOne, stringInputTwo, stringInputThree));
//				
//				InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(
//					      Context.INPUT_METHOD_SERVICE);
//				imm.hideSoftInputFromWindow(inputThree.getWindowToken(), 0);
//				
//				ExpandAnimation expandAnim = new ExpandAnimation(inputContainer, 500);
//				inputContainer.startAnimation(expandAnim);
//				
//				inputOne.setText(null);
//				inputTwo.setText(null);
//				inputThree.setText(null);
//				
//				inputOne.setHint(R.string.add_positive_one);
//				inputTwo.setHint(R.string.add_positive_two);
//				inputThree.setHint(R.string.add_positive_three);
//				
//				addButton.setText("L�gg till ny");
//			}
//		});

		ThreePosAdapter adapter = new ThreePosAdapter(mActivity.getLayoutInflater().getContext(), R.layout.three_positive_item, threePosItemList);
		setListAdapter(adapter);
	}
}