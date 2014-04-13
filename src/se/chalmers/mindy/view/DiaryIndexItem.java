package se.chalmers.mindy.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiaryIndexItem extends IndexListItem {
	private Fragment fragment;

	public DiaryIndexItem(Context context, Fragment fragment) {
		this(context, context.getString(R.string.diary), context.getString(R.string.index_diary_description), fragment);
	}

	public DiaryIndexItem(Context context, String name, Fragment fragment) {
		this(context, name, "", fragment);
	}

	public DiaryIndexItem(Context context, int nameResId, int descriptionResId, int audioContentResId, Fragment fragment) {
		this(context, context.getResources().getString(nameResId), context.getResources().getString(descriptionResId), fragment);
	}

	public DiaryIndexItem(Context context, String name, String description, Fragment fragment) {
		super(context, name, description);
		this.fragment = fragment;
	}

	@Override
	public List<View> getSubviews() {
		ArrayList<View> subviews = new ArrayList<View>();

		LinearLayout container = new LinearLayout(context);
		container.setOrientation(LinearLayout.VERTICAL);

		MindyDatabaseAdapter dbAdapter = new MindyDatabaseAdapter(context);
		dbAdapter.open();
		DiaryItem item = dbAdapter.fetchLatestNote();
		dbAdapter.close();

		Calendar currentTime = Calendar.getInstance();
		Calendar diaryTime = Calendar.getInstance();
		diaryTime.setTimeInMillis(item.getDate() == null ? Calendar.getInstance().getTimeInMillis() : item.getDate().getTimeInMillis());

		int currentDay = currentTime.get(Calendar.DAY_OF_YEAR);
		int positiveDay = diaryTime.get(Calendar.DAY_OF_YEAR);
		int currentYear = currentTime.get(Calendar.YEAR);
		int positiveYear = diaryTime.get(Calendar.YEAR);
		if (diaryTime != null && (currentDay - positiveDay != 1 || currentYear > positiveYear)) {
			setDescription(context.getString(R.string.index_diary_description_secondary));
		}
		TextView descriptionLabel = new TextView(context);
		formatTextView(descriptionLabel, 50, 40, 50, 40);
		String description = item.getDescription();
		if (description != null && description.length() > 0) {
			descriptionLabel.setText(description);
		} else {
			setTitle(context.getString(R.string.diary));
			setDescription(context.getString(R.string.diary_no_entries));
			descriptionLabel.setText(context.getString(R.string.diary_no_entries_desc));
		}
		container.addView(descriptionLabel);

		subviews.add(container);

		return subviews;
	}

	public void formatTextView(TextView textView, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
		textView.setTextSize(16.0f);
		textView.setTypeface(robotoLight);
		textView.setTextColor(context.getResources().getColor(R.color.text_color_dark));
		textView.setPadding(leftMargin, topMargin, rightMargin, bottomMargin);
	}

	@Override
	public OnClickListener getTitleOnClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (context instanceof MainActivity) {
					((MainActivity) context).pushFragment(fragment);
				}
			}
		};

	}
}
