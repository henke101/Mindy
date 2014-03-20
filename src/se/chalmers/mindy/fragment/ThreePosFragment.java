package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.ExerciseAdapter;
import se.chalmers.mindy.core.ExerciseItem;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.core.ThreePosAdapter;
import se.chalmers.mindy.core.ThreePosItem;
import se.chalmers.mindy.util.Expander;
import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ThreePosFragment extends ListFragment{

	MainActivity mActivity;
	SharedPreferences sharedPrefs;
	Editor editor;
	TextView expanded;
	
	ThreePosItem[] threePosItemList = {new ThreePosItem("L�gg Till","",""), new ThreePosItem("En Bra Sak", "En Annan Bra Sak", "Och Ytterliggare En Bra Sak")};
	
	public ThreePosFragment(){
	
	}
	
	public void onAttach(final Activity activity){
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = sharedPrefs.edit();
		
	}
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		ListView listView = getListView();
		
		View headerView = mActivity.getLayoutInflater().inflate(R.layout.fragment_threepositive, null);
		TextView titleView = (TextView) headerView.findViewById(R.id.pos_title);
		titleView.setText(R.string.app_name);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_light.ttf"));

		listView.addHeaderView(headerView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				if(position ==1){
					View expanding = view.findViewById(R.id.addExpanded); 
			        Expander expander = new Expander(expanding,500);
				}
			}
		});
		
		
		ThreePosAdapter adapter = new ThreePosAdapter(mActivity.getLayoutInflater().getContext(), R.layout.three_positive_item, threePosItemList);
		setListAdapter(adapter);
		System.out.println("onactivitycreated works");

	}


}