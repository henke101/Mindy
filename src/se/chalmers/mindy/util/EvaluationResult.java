package se.chalmers.mindy.util;

import java.util.ArrayList;

public class EvaluationResult {

	private final ArrayList<NameValuePair<Integer>> results;
	private int score;

	public EvaluationResult() {
		results = new ArrayList<NameValuePair<Integer>>();
		score = 0;
	}

	public void putResult(NameValuePair<Integer> nvp) {
		results.add(nvp);
	}

	public NameValuePair<Integer> getResultAtIndex(int index) {
		return results.get(index);
	}

	public ArrayList<NameValuePair<Integer>> getAllResults() {
		return results;
	}

}
