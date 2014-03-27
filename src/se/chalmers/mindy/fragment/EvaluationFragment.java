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
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class EvaluationFragment extends Fragment implements OnClickListener{

	private TextView tViewIntroText;
	private TextView tViewQuestionOne;
	private TextView tViewQuestionTwo;
	private TextView tViewQuestionThree;
	private TextView tViewQuestionFour;
	private TextView tViewLabelOneLeft;
	private TextView tViewLabelTwoLeft;
	private TextView tViewLabelThreeLeft;
	private TextView tViewLabelOneRight;
	private TextView tViewLabelTwoRight;
	private TextView tViewLabelThreeRight;
	private TextView tViewLabelFourOne;
	private TextView tViewLabelFourTwo;
	private TextView tViewLabelFourThree;
	//private TextView tViewLabelFourFour;
	private View view;
	private Typeface robotoCondensedLight;
	private Typeface robotoLight;
	private RadioGroup answersOne;
	private RadioGroup answersTwo;
	private RadioGroup answersThree;
	private CheckBox checkBoxOne;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_evaluation, null);
		tViewIntroText = (TextView) view.findViewById(R.id.eval_intro_text);
		tViewQuestionOne = (TextView) view.findViewById(R.id.eval_question_1);
		tViewQuestionTwo = (TextView) view.findViewById(R.id.eval_question_2);
		tViewQuestionThree = (TextView) view.findViewById(R.id.eval_question_3);
		tViewQuestionFour = (TextView) view.findViewById(R.id.eval_question_4);
		tViewLabelOneLeft = (TextView) view.findViewById(R.id.eval_question_1_label_left);
		tViewLabelOneRight = (TextView) view.findViewById(R.id.eval_question_1_label_right);
		tViewLabelTwoLeft = (TextView) view.findViewById(R.id.eval_question_2_label_left);
		tViewLabelTwoRight = (TextView) view.findViewById(R.id.eval_question_2_label_right);
		tViewLabelThreeLeft = (TextView) view.findViewById(R.id.eval_question_3_label_left);
		tViewLabelThreeRight = (TextView) view.findViewById(R.id.eval_question_3_label_right);
		tViewLabelFourOne = (TextView) view.findViewById(R.id.eval_question_4_label_1);
		tViewLabelFourTwo = (TextView) view.findViewById(R.id.eval_question_4_label_2);
		tViewLabelFourThree = (TextView) view.findViewById(R.id.eval_question_4_label_3);
		//tViewLabelFourFour = (TextView) view.findViewById(R.id.eval_question_4_label_4);
		answersOne = (RadioGroup) view.findViewById(R.id.eval_answers_1);
		answersTwo = (RadioGroup) view.findViewById(R.id.eval_answers_2);
		answersThree = (RadioGroup) view.findViewById(R.id.eval_answers_3);
		
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
		
		tViewIntroText.setTypeface(robotoCondensedLight);
		tViewQuestionOne.setTypeface(robotoCondensedLight);
		tViewQuestionTwo.setTypeface(robotoCondensedLight);
		tViewQuestionThree.setTypeface(robotoCondensedLight);
		tViewQuestionFour.setTypeface(robotoCondensedLight);
		tViewLabelOneLeft.setTypeface(robotoCondensedLight);
		tViewLabelOneRight.setTypeface(robotoCondensedLight);
		tViewLabelTwoLeft.setTypeface(robotoCondensedLight);
		tViewLabelTwoRight.setTypeface(robotoCondensedLight);
		tViewLabelThreeLeft.setTypeface(robotoCondensedLight);
		tViewLabelThreeRight.setTypeface(robotoCondensedLight);
		tViewLabelFourOne.setTypeface(robotoCondensedLight);
		tViewLabelFourTwo.setTypeface(robotoCondensedLight);
		tViewLabelFourThree.setTypeface(robotoCondensedLight);
		//tViewLabelFourFour.setTypeface(robotoCondensedLight);

		
		return view;
	}

	@Override
	public void onClick(View v) {
	
		
	}
	
}
