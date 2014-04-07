package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PomodoroFragment extends Fragment implements OnClickListener {

	private MainActivity mActivity;
	private Button mTimerButton;
	private CountDownTimer pomodoroTimer;
	private ProgressBar mProgressBar;
	private TextView mLabel;

	int count = 0;
	int buttonMode = 0;
	int runCount = 0;

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mActivity.setActionBarBackgroundTransparency(255);

		View parent = mActivity.getLayoutInflater().inflate(R.layout.fragment_pomodoro, null);

		TextView titleView = (TextView) parent.findViewById(R.id.header_title);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));

		mLabel = (TextView) parent.findViewById(R.id.timer_label);
		mLabel.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_light.ttf"));

		mProgressBar = (ProgressBar) parent.findViewById(R.id.pomodoro_timer);

		mTimerButton = (Button) parent.findViewById(R.id.start);
		mTimerButton.setOnClickListener(this);

		return parent;
	}

	@Override
	public void onResume() {
		super.onResume();
		mActivity.setActionBarBackgroundTransparency(255);
		mTimerButton.setText(buttonMode == 0 ? R.string.pomodoro_timer_start : R.string.pomodoro_timer_stop);
	}

	private CountDownTimer getRunningTimer(final int minutes) {

		mProgressBar.setMax(60 * minutes * 100);
		mProgressBar.setProgress(60 * minutes * 100);

		return new CountDownTimer(60 * minutes * 1000, 10) {
			int prevSeconds = -1;

			@Override
			public void onTick(long leftTimeInMilliseconds) {
				int seconds = (int) leftTimeInMilliseconds / 1000;

				// Only update the text view if the content will be different (i.e. if we have progressed [at least] a second)
				if (prevSeconds != seconds) {
					String paddedSeconds = seconds % 60 < 10 ? "0" + seconds % 60 : "" + seconds % 60;
					mLabel.setText(seconds / 60 + ":" + paddedSeconds);
					prevSeconds = seconds;
				}

				int progress = (int) leftTimeInMilliseconds / 10;
				if (count == 0) {
					mProgressBar.setProgress(progress);
				} else if (count == 1) {
					mProgressBar.setProgress(mProgressBar.getMax() - progress);
				}
			}

			@Override
			public void onFinish() {
				count = (count + 1) % 2;

				// Get vibrator service
				Vibrator v = (Vibrator) mActivity.getSystemService(Context.VIBRATOR_SERVICE);
				// Vibrate for 500 milliseconds
				v.vibrate(500);

				if (count == 1) {
					pomodoroTimer = getRunningTimer(5);
				} else if (count == 0) {
					pomodoroTimer = getRunningTimer(25);
				}

				mProgressBar.setProgress(0);
				mTimerButton.setText(R.string.pomodoro_timer_start);
			}
		}.start();

	}

	@Override
	public void onClick(View v) {
		buttonMode = (buttonMode + 1) % 2;

		if (buttonMode == 1) {
			pomodoroTimer = getRunningTimer(25);
			mTimerButton.setText(R.string.pomodoro_timer_stop);
		} else if (buttonMode == 0) {
			mTimerButton.setText(R.string.pomodoro_timer_start);
			pomodoroTimer.cancel();
		}

	}
}
