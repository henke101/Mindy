package se.chalmers.mindy.view;

import java.util.Calendar;

public class ThreePosItem implements Comparable {

	private String positiveOne, positiveTwo, positiveThree;
	private Calendar cDate;
	
	public ThreePosItem(String positiveOne, String positiveTwo, String positiveThree) {
		this(null, positiveOne, positiveTwo, positiveThree);
	}

	public ThreePosItem(Calendar cDate, String positiveOne, String positiveTwo, String positiveThree ){
		this.cDate = cDate;
		this.positiveOne = positiveOne;
		this.positiveTwo = positiveTwo;
		this.positiveThree = positiveThree;
	}

	@Override
	public int compareTo(Object another) {
		if(another == null)
			return -1;		
		try{
			another = (ThreePosItem)another;
		}catch(Exception e){
			return -2;
		}
		
		if(((ThreePosItem) another).getDate().equals(this.getDate())){
			return 1;
		}
		return 0;
	}
	
	public String getPositiveOne() {
		return positiveOne;
	}
	
	public String getPositiveTwo() {
		return positiveTwo;
	}
	
	public String getPositiveThree() {
		return positiveThree;
	}
	
	public Calendar getDate() {
		return cDate;
	}

	public void setPositiveOne(String positiveOne) {
		this.positiveOne = positiveOne;
	}

	public void setPositiveTwo(String positiveTwo) {
		this.positiveTwo = positiveTwo;
	}

	public void setPositiveThree(String positiveThree) {
		this.positiveThree = positiveThree;
	}

	public void setDate(Calendar cDate){
		this.cDate = cDate;
	}

}
