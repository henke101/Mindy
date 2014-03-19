package se.chalmers.mindy.core;

public class ThreePosItem implements Comparable {

	private String date, positiveOne, positiveTwo, positiveThree;

	public ThreePosItem(String positiveOne, String positiveTwo, String positiveThree ){
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
	
	public String getDate() {
		return date;
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

	public void setDate(String date){
		this.date = date;
	}

}
