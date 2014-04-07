package se.chalmers.mindy.util;

import se.chalmers.mindy.view.Constants;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;

public class MediaPlayerService extends Service {

	private MediaPlayer mediaPlayer;
	private Intent intent;

	private final IBinder myBinder = new MyLocalBinder();

	@Override
	public IBinder onBind(Intent arg0) {
		return myBinder;
	}

	public class MyLocalBinder extends Binder {
		public MediaPlayerService getService() {
			return MediaPlayerService.this;
		}
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.intent = intent;
		initializePlayer();
		return Service.START_STICKY;
	}

	@Override
	public void onDestroy() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}

	}

	public void initializePlayer() {
		mediaPlayer = MediaPlayer.create(MediaPlayerService.this, intent.getExtras().getInt(Constants.MEDIA_AUDIO_ID));
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);

	}

	public void startPlayback() {
		if (!mediaPlayer.isPlaying()) {
			mediaPlayer.start();
		}
	}

	public void pausePlayback() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
		}
	}

	public void stopPlayback() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.prepareAsync();
		}
	}

	public void setPlaybackPosition(int position) {
		mediaPlayer.seekTo(position);
	}

	public int getPlaybackPosition() {
		return mediaPlayer.getCurrentPosition();
	}
}
