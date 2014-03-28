package se.chalmers.mindy.util;

import java.util.ArrayList;

public class EvaluationResult {

	private final ArrayList<NameValuePair<Integer>> mindfulnessResults;
	private final ArrayList<NameValuePair<Integer>> studyTechniqueResults;
	private final ArrayList<NameValuePair<Integer>> positivePsychologyResults;
	private int score;

	public EvaluationResult() {
		mindfulnessResults = new ArrayList<NameValuePair<Integer>>();
		studyTechniqueResults = new ArrayList<NameValuePair<Integer>>();
		positivePsychologyResults = new ArrayList<NameValuePair<Integer>>();
		score = 0;
	}

	public void putMindulnessResult(NameValuePair<Integer> nvp) {
		mindfulnessResults.add(nvp);
	}
	public void putStudyTechniqueResult(NameValuePair<Integer> nvp) {
		studyTechniqueResults.add(nvp);
	}
	public void putPositivePsychologyResult(NameValuePair<Integer> nvp) {
		positivePsychologyResults.add(nvp);
	}

	public NameValuePair<Integer> getMindfulnessResultAtIndex(int index) {
		return mindfulnessResults.get(index);
	}
	public NameValuePair<Integer> getStudyTechniqueResultAtIndex(int index) {
		return studyTechniqueResults.get(index);
	}
	public NameValuePair<Integer> getPositivePsychologyResultAtIndex(int index) {
		return positivePsychologyResults.get(index);
	}

	public ArrayList<NameValuePair<Integer>> getAllMindfulnessResults() {
		return mindfulnessResults;
	}
	public ArrayList<NameValuePair<Integer>> getAllStudyTechniqueResults() {
		return studyTechniqueResults;
	}
	public ArrayList<NameValuePair<Integer>> getAllPositivePsychologyResults() {
		return positivePsychologyResults;
	}

}
