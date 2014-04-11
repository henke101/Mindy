package se.chalmers.mindy.view;

import java.util.Calendar;

import android.content.Context;

public class DiaryItem extends AbsListItem {
	private String title, body;
	private Calendar date;

	public DiaryItem(Context context, String title, String body, Calendar date) {
		super(context, title, body);
		this.title = title;
		this.body = body;
		setDate(date);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
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
