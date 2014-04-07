package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class SleepingPillFragment extends Fragment implements Runnable, OnClickListener {
	private MediaPlayer mediaPlayer;
	private View view;
	private IntentFilter mediaFilter;
	private ProgressBar audioProgressBar;
	private TextView info;
	private TextView title;
	private Button playPauseButton;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_sleepingpill, null);

		//View circProgressBar = view.findViewById(R.drawable.circular_progress_bar);
		//startAudio = (Button) circProgressBar.findViewById(R.id.rotating_play_button);

		Typeface robotoConLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_condensed_light.ttf");

		info = (TextView) view.findViewById(R.id.info_audio);
		title = (TextView) view.findViewById(R.id.title_audio);

		info.setTypeface(robotoConLight);
		title.setTypeface(robotoConLight);

		audioProgressBar = (ProgressBar) view.findViewById(R.id.audio_progress_bar);
		audioProgressBar.setClickable(true);

		playPauseButton = (Button) view.findViewById(R.id.play_pause_button);

		playPauseButton.setOnClickListener(this);

		//mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);

		mediaFilter = new IntentFilter(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY);
		mediaFilter.setPriority(1000);
		getActivity().getApplicationContext().registerReceiver(audioIntentReceiver, mediaFilter);



		return view;
	}

	@Override
	public void onPause(){
		super.onPause();
		if (mediaPlayer != null){
			mediaPlayer.pause();
		}
	}
	@Override
	public void onStop(){
		super.onStop();
		if (mediaPlayer != null){
			mediaPlayer.release();		
			mediaPlayer = null;
		}
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
		if (mediaPlayer != null){
			mediaPlayer.release();		
			mediaPlayer = null;
		}
		getActivity().getApplicationContext().unregisterReceiver(audioIntentReceiver);
	}

	private final BroadcastReceiver audioIntentReceiver = new BroadcastReceiver(){
		public void onReceive(Context ctx, Intent intent) {
			if (intent.getAction().equals(
					android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
				onPause();
			}
		}
	};

	@Override
	public void run() {
		int currentPosition= 0;
		int duration = mediaPlayer.getDuration();
		while (mediaPlayer!=null && currentPosition<duration-999) {
			try {
				Thread.sleep(1000);
				currentPosition= mediaPlayer.getCurrentPosition();
			} catch (InterruptedException e) {
				return;
			} catch (Exception e) {
				return;
			}            
			audioProgressBar.setProgress(currentPosition);
		}
		audioProgressBar.setProgress(duration);
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mediaPlayer = null;  
				playPauseButton.setBackgroundResource(R.drawable.play_button);
			}
		});
	}

	@Override
	public void onClick(View v) {

		if (v.equals(playPauseButton)) {
			if (mediaPlayer != null && mediaPlayer.isPlaying()){
				mediaPlayer.pause();
				playPauseButton.setBackgroundResource(R.drawable.play_button);
			}
			else{ 

				if (mediaPlayer == null){
					mediaPlayer = MediaPlayer.create(getActivity(), R.raw.sample_soundfile);                
					audioProgressBar.setProgress(0);
					audioProgressBar.setMax(mediaPlayer.getDuration());
					new Thread(this).start();
				}
				playPauseButton.setBackgroundResource(R.drawable.pause_button);
				mediaPlayer.start();
			}
		}
	}
}



