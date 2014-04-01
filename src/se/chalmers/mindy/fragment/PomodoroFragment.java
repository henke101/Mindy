package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PomodoroFragment extends Fragment{
 
	private MainActivity mActivity;
	private SharedPreferences sharedPrefs;
	private Editor editor;
	private Button playButton;
	private CountDownTimer pomodoroTimer;
	private int barMax;
	private ProgressDialog barTimer;
	private Animation an;
	private ProgressBar pb;
	
	@Override
	public void onAttach(final Activity activity) {
		super.onAttach(activity);

		mActivity = (MainActivity) activity; 
		sharedPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
		editor = sharedPrefs.edit();	
	
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		View pomodoroView = mActivity.getLayoutInflater().inflate(R.layout.fragment_pomodoro, null);
		TextView titleView = (TextView) pomodoroView.findViewById(R.id.header_title);
		playButton = (Button)pomodoroView.findViewById(R.id.start);
		titleView.setText(R.string.app_name);
		titleView.setTypeface(Typeface.createFromAsset(mActivity.getAssets(), "fonts/roboto_light.ttf"));
		
		pb = (ProgressBar) pomodoroView.findViewById(R.id.pomodoro_timer);

		an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
		an.setFillAfter(true);
		
		playButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(playButton.getText().equals("Start")){
					playButton.setText("Stop");
					startTimer(25);
					pb.startAnimation(an);
				}
				if(playButton.getText().equals("Stop")){
					playButton.setText("Start");
					pomodoroTimer.cancel();
					pb.clearAnimation();
				}
			}
		});
	}

	private void startTimer(final int min) {
		pomodoroTimer = new CountDownTimer(60 * min * 1000, 500) {

			@Override
			public void onTick(long leftTimeInMilliseconds) {
				long seconds = leftTimeInMilliseconds / 1000;
				int barVal= (barMax) - ((int)(seconds/60*100)+(int)(seconds%60));
				barTimer.setProgress(barVal);

			}
			@Override
			public void onFinish() {
				barTimer.setProgress(0);
				pb.clearAnimation();
				playButton.setText("Start");
			}
		}.start();

	}

}
