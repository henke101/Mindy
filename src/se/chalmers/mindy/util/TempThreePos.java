package se.chalmers.mindy.util;

import java.util.Calendar;

public class TempThreePos {
	private String first, second, third;
	private Calendar date;

	public TempThreePos(String first, String second, String third) {
		this(first, second, third, null);
	}

	public TempThreePos(String first, String second, String third, Calendar date) {
		super();
		this.first = first;
		this.second = second;
		this.third = third;
		this.date = date;
	}

	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setSecond(String second) {
		this.second = second;
	}

	public String getThird() {
		return third;
	}

	public void setThird(String third) {
		this.third = third;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

}
