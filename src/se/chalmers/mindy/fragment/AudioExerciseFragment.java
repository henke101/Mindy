package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.util.MediaPlayerService;
import se.chalmers.mindy.util.MediaPlayerService.MyLocalBinder;
import se.chalmers.mindy.view.Constants;
import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class AudioExerciseFragment extends Fragment implements Runnable, OnClickListener {
	private MediaPlayer mediaPlayer;
	private View view;
	private IntentFilter mediaFilter;
	private ProgressBar audioProgressBar;
	private TextView info;
	private TextView title;
	private Button playPauseButton;
	private MediaPlayerService mpService;
	private int audioID;
	private int mPlaybackPosition;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		((MainActivity) activity).setActionBarBackgroundTransparency(255);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_audio_exercise, null);

		// View circProgressBar = view.findViewById(R.drawable.circular_progress_bar);
		// startAudio = (Button) circProgressBar.findViewById(R.id.rotating_play_button);

		// Typeface robotoConLight = Typeface.createFromAsset(getActivity().getAssets(),"fonts/roboto_condensed_light.ttf");

		Typeface robotoLight = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto_light.ttf");

		Bundle bundle = getArguments();
		audioID = bundle.getInt(Constants.MEDIA_AUDIO_ID);
		startMPService();

		int titleID = bundle.getInt(Constants.MEDIA_TITLE_ID);
		int infoID = bundle.getInt(Constants.MEDIA_INFO_ID);

		// Get the playback position. Defaults to 0 if not supplied
		mPlaybackPosition = bundle.getInt(Constants.MEDIA_PROGRESS, 0);

		info = (TextView) view.findViewById(R.id.info_audio);
		title = (TextView) view.findViewById(R.id.title_audio);

		// info.setTypeface(robotoConLight);
		// title.setTypeface(robotoConLight);

		info.setTypeface(robotoLight);
		info.setText(infoID);
		title.setTypeface(robotoLight);
		title.setText(titleID);

		audioProgressBar = (ProgressBar) view.findViewById(R.id.audio_progress_bar);
		audioProgressBar.setClickable(true);
		audioProgressBar.setProgress(mPlaybackPosition);

		playPauseButton = (Button) view.findViewById(R.id.play_pause_button);
		playPauseButton.setOnClickListener(this);

		// mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);

		mediaFilter = new IntentFilter(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY);
		mediaFilter.setPriority(1000);
		// mpService = new MediaPlayerService();

		getActivity().getApplicationContext().registerReceiver(audioIntentReceiver, mediaFilter);

		return view;
	}

	private void startMPService() {
		Intent intent = new Intent(new Intent(getActivity().getApplicationContext(), MediaPlayerService.class));
		intent.putExtra("audioID", audioID);
		getActivity().startService(intent);
		getActivity().bindService(intent, mpConnection, Context.BIND_AUTO_CREATE);

	}

	@Override
	public void onPause() {
		super.onPause();
		if (mediaPlayer != null) {
			mediaPlayer.pause();
			playPauseButton.setBackgroundResource(R.drawable.play_button);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().getApplicationContext().unregisterReceiver(audioIntentReceiver);
	}

	private final BroadcastReceiver audioIntentReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context ctx, Intent intent) {
			if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
				onPause();
			}
		}
	};

	/**
	 * Handles the progression of the progress bar
	 */
	@Override
	public void run() {
		int currentPosition = 0;
		int duration = mediaPlayer.getDuration();
		while (mediaPlayer != null && currentPosition < duration - 999) {
			try {
				Thread.sleep(1000);
				currentPosition = mediaPlayer.getCurrentPosition();
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
			if (mediaPlayer != null && mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
				playPauseButton.setBackgroundResource(R.drawable.play_button);
			} else {
				if (mediaPlayer == null) {
					mediaPlayer = mpService.getMediaPlayer();
					mpService.setPlaybackPosition(mPlaybackPosition);

					audioProgressBar.setProgress(mPlaybackPosition);
					audioProgressBar.setMax(mediaPlayer.getDuration());
					new Thread(this).start();
				}
				playPauseButton.setBackgroundResource(R.drawable.pause_button);

				mediaPlayer.start();
			}
		}
	}

	private ServiceConnection mpConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			MyLocalBinder binder = (MyLocalBinder) service;
			mpService = binder.getService();
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
		}

	};

}
