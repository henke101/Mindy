package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.util.EvaluationResult;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import se.chalmers.mindy.util.NameValuePair;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class EvaluationFragment extends Fragment implements OnCheckedChangeListener, OnClickListener{

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
	private RadioButton techniqueOne;
	private RadioButton techniqueTwo;
	private RadioButton techniqueThree;
	private RadioButton techniqueFour;
	private RadioButton balanceOne;
	private RadioButton balanceTwo;
	private RadioButton balanceThree;
	private RadioButton balanceFour;
	private RadioButton balanceFive;
	private RadioButton resultsOne;
	private RadioButton resultsTwo;
	private RadioButton resultsThree;
	private RadioButton resultsFour;
	private CheckBox checkBoxOne;
	private CheckBox checkBoxTwo;
	private CheckBox checkBoxThree;
	private CheckBox checkBoxFour;
	private Button submitButton;
	private EvaluationResult result;
	private NameValuePair<Integer> nvpStudyTechnique;
	private NameValuePair<Integer> nvpStudyBalance;
	private NameValuePair<Integer> nvpStudyResults;
	private NameValuePair<Integer> nvpTechniqueImpact;

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
		techniqueOne = (RadioButton) view.findViewById(R.id.answers_1_option_1);
		techniqueTwo = (RadioButton) view.findViewById(R.id.answers_1_option_2);
		techniqueThree = (RadioButton) view.findViewById(R.id.answers_1_option_3);
		techniqueFour = (RadioButton) view.findViewById(R.id.answers_1_option_4);
		balanceOne = (RadioButton) view.findViewById(R.id.answers_2_option_1);
		balanceTwo = (RadioButton) view.findViewById(R.id.answers_2_option_2);
		balanceThree = (RadioButton) view.findViewById(R.id.answers_2_option_3);
		balanceFour = (RadioButton) view.findViewById(R.id.answers_2_option_4);
		balanceFive = (RadioButton) view.findViewById(R.id.answers_2_option_5);
		resultsOne = (RadioButton) view.findViewById(R.id.answers_3_option_1);
		resultsTwo = (RadioButton) view.findViewById(R.id.answers_3_option_2);
		resultsThree = (RadioButton) view.findViewById(R.id.answers_3_option_3);
		resultsFour = (RadioButton) view.findViewById(R.id.answers_3_option_4);

		submitButton = (Button) view.findViewById(R.id.eval_submit_button);
		techniqueOne.setOnCheckedChangeListener(this);
		techniqueTwo.setOnCheckedChangeListener(this);
		techniqueThree.setOnCheckedChangeListener(this);
		techniqueFour.setOnCheckedChangeListener(this);
		balanceOne.setOnCheckedChangeListener(this);
		balanceTwo.setOnCheckedChangeListener(this);
		balanceThree.setOnCheckedChangeListener(this);
		balanceFour.setOnCheckedChangeListener(this);
		balanceFive.setOnCheckedChangeListener(this);
		resultsOne.setOnCheckedChangeListener(this);
		resultsTwo.setOnCheckedChangeListener(this);
		resultsThree.setOnCheckedChangeListener(this);
		resultsFour.setOnCheckedChangeListener(this);
		submitButton.setEnabled(false);
		submitButton.setOnClickListener(this);

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
		submitButton.setTypeface(robotoCondensedLight);
		//tViewLabelFourFour.setTypeface(robotoCondensedLight);

		result = new EvaluationResult();

		return view;
	}

	@Override
	public void onClick(View v) {
		Log.v(getTag(), "Reached listener");
		int clicked =v.getId();
		switch(clicked){
		case R.id.eval_submit_button:
			Log.v(getTag(), "Reached id");
			//I posted the results in these categories for now, this might be redone later
			//Some results might be both mindfulness and study technique for example
			if (nvpTechniqueImpact != null){
				result.putMindulnessResult(nvpTechniqueImpact);
			}
			result.putMindulnessResult(nvpStudyResults);
			result.putStudyTechniqueResult(nvpStudyTechnique);
			result.putStudyTechniqueResult(nvpStudyBalance);

			MindyDatabaseAdapter mda = new MindyDatabaseAdapter(getActivity());
			mda.open();
			mda.insertNewTestResults(result);
			mda.close();

			Fragment indexFragment = new IndexFragment();
			// Insert the fragment by replacing any existing fragment
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.content_frame, indexFragment).commit();

			Log.v(getTag(),"" + nvpStudyBalance.getName() + "\n" +
					nvpStudyTechnique.getName() + "\n" + nvpStudyResults.getName());

			break;

		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		switch(buttonView.getId()){
		case R.id.answers_1_option_1:
			nvpStudyTechnique = new NameValuePair<Integer>("study_technique", 0);
			break;
		case R.id.answers_1_option_2:
			nvpStudyTechnique = new NameValuePair<Integer>("study_technique", 1);
			break;
		case R.id.answers_1_option_3:
			nvpStudyTechnique = new NameValuePair<Integer>("study_technique", 2);
			break;
		case R.id.answers_1_option_4:
			nvpStudyTechnique = new NameValuePair<Integer>("study_technique", 3);
			break;
		case R.id.answers_2_option_1:
			nvpStudyBalance = new NameValuePair<Integer>("study_balance", 0);
			break;
		case R.id.answers_2_option_2:
			nvpStudyBalance = new NameValuePair<Integer>("study_balance", 1);
			break;
		case R.id.answers_2_option_3:
			nvpStudyBalance = new NameValuePair<Integer>("study_balance", 2);
			break;
		case R.id.answers_2_option_4:
			nvpStudyBalance = new NameValuePair<Integer>("study_balance", 3);
			break;
		case R.id.answers_2_option_5:
			nvpStudyBalance = new NameValuePair<Integer>("study_balance", 4);
			break;
		case R.id.answers_3_option_1:
			nvpStudyResults = new NameValuePair<Integer>("study_results", 0);
			break;
		case R.id.answers_3_option_2:
			nvpStudyResults = new NameValuePair<Integer>("study_results", 1);
			break;
		case R.id.answers_3_option_3:
			nvpStudyResults = new NameValuePair<Integer>("study_results", 2);
			break;
		case R.id.answers_3_option_4:
			nvpStudyResults = new NameValuePair<Integer>("study_results", 3);
			break;
		case R.id.answers_4_option_1:
			if(isChecked){
				nvpTechniqueImpact = new NameValuePair<Integer>("sleep", 1);
			}
			else{

				nvpTechniqueImpact = new NameValuePair<Integer>("sleep", 0);
			}
			break;
		case R.id.answers_4_option_2:
			if(isChecked){
				nvpTechniqueImpact = new NameValuePair<Integer>("confidence", 1);
			}
			else{

				nvpTechniqueImpact = new NameValuePair<Integer>("confidence", 0);
			}
			break;
		case R.id.answers_4_option_3:
			if(isChecked){
				nvpTechniqueImpact = new NameValuePair<Integer>("insecurity", 1);
			}
			else{

				nvpTechniqueImpact = new NameValuePair<Integer>("insecurity", 0);
			}
			break;
		}
		if (nvpStudyResults != null && nvpStudyTechnique != null && nvpStudyBalance != null){
			submitButton.setEnabled(true);
		}


	}

}
