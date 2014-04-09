package se.chalmers.mindy.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.core.ThreePosItem;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThreePositiveIndexItem extends IndexListItem {
	private Fragment fragment;

	public ThreePositiveIndexItem(Context context, Fragment fragment) {
		this(context, context.getString(R.string.index_threepos_title), context.getString(R.string.index_threepos_description), fragment);
	}

	public ThreePositiveIndexItem(Context context, String name, Fragment fragment) {
		this(context, name, "", fragment);
	}

	public ThreePositiveIndexItem(Context context, int nameResId, int descriptionResId, int audioContentResId, Fragment fragment) {
		this(context, context.getResources().getString(nameResId), context.getResources().getString(descriptionResId), fragment);
	}

	public ThreePositiveIndexItem(Context context, String name, String description, Fragment fragment) {
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
		ThreePosItem threePos = dbAdapter.fetchLatestPositive();
		dbAdapter.close();

		Calendar currentTime = Calendar.getInstance();
		Calendar positiveTime = Calendar.getInstance();
		positiveTime.setTimeInMillis(threePos.getDate() == null ? Calendar.getInstance().getTimeInMillis() : threePos.getDate().getTimeInMillis());

		int currentDay = currentTime.get(Calendar.DAY_OF_YEAR);
		int positiveDay = positiveTime.get(Calendar.DAY_OF_YEAR);
		int currentYear = currentTime.get(Calendar.YEAR);
		int positiveYear = positiveTime.get(Calendar.YEAR);
		if (positiveTime != null && (currentDay - positiveDay != 1 || currentYear > positiveYear)) {
			setDescription(context.getString(R.string.index_threepos_description_secondary));
		}

		if (threePos.getPositiveOne() != null && threePos.getPositiveOne().length() > 0) {
			TextView firstLabel = new TextView(context);
			firstLabel.setText("1. " + threePos.getPositiveOne());
			formatTextView(firstLabel, 50, 40, 50, 10);
			container.addView(firstLabel);
		}

		if (threePos.getPositiveTwo() != null && threePos.getPositiveTwo().length() > 0) {
			TextView secondLabel = new TextView(context);
			secondLabel.setText("2. " + threePos.getPositiveTwo());
			formatTextView(secondLabel, 50, 15, 50, 10);
			container.addView(secondLabel);
		}

		if (threePos.getPositiveThree() != null && threePos.getPositiveThree().length() > 0) {
			TextView thirdLabel = new TextView(context);
			thirdLabel.setText("3. " + threePos.getPositiveThree());
			formatTextView(thirdLabel, 50, 15, 50, 40);
			container.addView(thirdLabel);
		}

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
