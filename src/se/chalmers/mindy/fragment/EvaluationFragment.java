package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class EvaluationFragment extends Fragment implements OnClickListener{

	private TextView tViewQuestionOne;
	private TextView tViewQuestionTwo;
	private View view;
	private Typeface robotoCondensedLight;
	private Typeface robotoLight;
	private RadioGroup answersOne;
	private RadioGroup answersTwo;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_evaluation, null);
		tViewQuestionOne = (TextView) view.findViewById(R.id.eval_question_1);
		tViewQuestionTwo = (TextView) view.findViewById(R.id.eval_question_2);
		answersOne = (RadioGroup) view.findViewById(R.id.eval_answers_1);
		answersTwo = (RadioGroup) view.findViewById(R.id.eval_answers_2);
		
		answersOne.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		    @Override
		    public void onCheckedChanged(RadioGroup group, int checkedId) {
		        Log.v(getTag(), "Listener reached");
		    }
		});
		answersTwo.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		    @Override
		    public void onCheckedChanged(RadioGroup group, int checkedId) {
		        switch(checkedId){
		            // Your code    
		        }   
		    }
		});
		
		robotoLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_light.ttf");
		robotoCondensedLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_condensed_light.ttf");
		
		tViewQuestionOne.setTypeface(robotoCondensedLight);
		tViewQuestionTwo.setTypeface(robotoCondensedLight);
		
		return view;
	}

	@Override
	public void onClick(View v) {
	
		
	}
	
}
