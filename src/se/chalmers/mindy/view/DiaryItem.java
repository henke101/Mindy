package se.chalmers.mindy.view;

import java.util.Calendar;

public class DiaryItem {
	private String title, body;
	private Calendar date;

	public DiaryItem(String title, String body, Calendar date) {
		super();
		this.title = title;
		this.body = body;
		this.setDate(date);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
