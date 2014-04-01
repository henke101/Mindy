package se.chalmers.mindy.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import se.chalmers.mindy.R;
import se.chalmers.mindy.util.MindyDatabaseAdapter;
import se.chalmers.mindy.util.TempThreePos;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThreePositiveIndexItem extends IndexListItem {

	public ThreePositiveIndexItem(Context context) {
		this(context, context.getString(R.string.index_threepos_title), context.getString(R.string.index_threepos_description));
	}

	public ThreePositiveIndexItem(Context context, String name) {
		this(context, name, null);
	}

	public ThreePositiveIndexItem(Context context, String name, String description) {
		super(context, name, description);
	}

	@Override
	public List<View> getSubviews() {
		ArrayList<View> subviews = new ArrayList<View>();

		LinearLayout container = new LinearLayout(context);
		container.setOrientation(LinearLayout.VERTICAL);

		MindyDatabaseAdapter dbAdapter = new MindyDatabaseAdapter(context);
		dbAdapter.open();
		TempThreePos threePos = dbAdapter.fetchLatestPositive();

		Calendar currentTime = Calendar.getInstance();
		Calendar positiveTime = threePos.getDate();

		if (currentTime.get(Calendar.DAY_OF_YEAR) - positiveTime.get(Calendar.DAY_OF_YEAR) > 0) {
			setDescription(context.getString(R.string.index_threepos_description_secondary));
		}

		TextView firstLabel = new TextView(context);
		firstLabel.setText("1. " + threePos.getFirst());
		formatTextView(firstLabel, 50, 20, 50, 5);
		container.addView(firstLabel);

		TextView secondLabel = new TextView(context);
		secondLabel.setText("2. " + threePos.getSecond());
		formatTextView(secondLabel, 50, 5, 50, 5);
		container.addView(secondLabel);

		TextView thirdLabel = new TextView(context);
		thirdLabel.setText("3. " + threePos.getThird());
		formatTextView(thirdLabel, 50, 5, 50, 20);
		container.addView(thirdLabel);

		subviews.add(container);

		return subviews;
	}

	public void formatTextView(TextView textView, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
		textView.setTextSize(19.0f);
		textView.setTypeface(robotoThin);
		textView.setPadding(leftMargin, topMargin, rightMargin, bottomMargin);
	}
}
