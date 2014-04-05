package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PomodoroFragment extends Fragment {

	private MainActivity mActivity;
	private SharedPreferences sharedPrefs;
	private Button playButton;
	private CountDownTimer pomodoroTimer;
	private Animation an;
	private ProgressBar pb;
	private TextView mLabel;

	int count = 0;

	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity;
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View parent = mActivity.getLayoutInflater().inflate(R.layout.fragment_pomodoro, null);

		TextView titleView = (TextView) parent.findViewById(R.id.header_title);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_thin.ttf"));

		mLabel = (TextView) parent.findViewById(R.id.timer_label);

		playButton = (Button) parent.findViewById(R.id.start);
		pb = (ProgressBar) parent.findViewById(R.id.pomodoro_timer);

		an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
		an.setFillAfter(true);

		playButton.setOnClickListener(new OnClickListener() {
			int buttonMode = 0;

			@Override
			public void onClick(View v) {

				buttonMode = (buttonMode + 1) % 2;

				if (buttonMode == 1) {
					pb.startAnimation(an);
					pomodoroTimer = getRunningTimer(25);
					playButton.setText("Stop");
				} else if (buttonMode == 0) {
					playButton.setText("Start");
					pomodoroTimer.cancel();
					pb.clearAnimation();
				}
			}
		});

		return parent;
	}

	@Override
	public void onResume() {
		super.onResume();
		mActivity.setActionBarBackgroundTransparency(255);
	}

	private CountDownTimer getRunningTimer(final int minutes) {

		pb.setMax(60 * minutes * 100);
		pb.setProgress(60 * minutes * 100);

		return new CountDownTimer(60 * minutes * 1000, 10) {

			@Override
			public void onTick(long leftTimeInMilliseconds) {
				int seconds = (int) leftTimeInMilliseconds / 1000;

				String paddedSeconds = seconds % 60 < 10 ? "0" + seconds % 60 : "" + seconds % 60;
				mLabel.setText(seconds / 60 + ":" + paddedSeconds);

				int progress = (int) leftTimeInMilliseconds / 10;
				if (count == 0) {
					pb.setProgress(progress);
				} else if (count == 1) {
					pb.setProgress(pb.getMax() - progress);
				}
			}

			@Override
			public void onFinish() {
				count = (count + 1) % 2;
				if (count == 1) {
					pomodoroTimer = getRunningTimer(5);
				} else if (count == 0) {
					pomodoroTimer = getRunningTimer(25);
				}

				pb.setProgress(0);
				playButton.setText("Start");
			}
		}.start();

	}
}
