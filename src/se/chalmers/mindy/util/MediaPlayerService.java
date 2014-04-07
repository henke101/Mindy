package se.chalmers.mindy.util;


import se.chalmers.mindy.R;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

public class MediaPlayerService extends Service{

	private MediaPlayer mediaPlayer;

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

	public MediaPlayerService() {

	}
	public MediaPlayer getMediaPlayer(){
		return mediaPlayer;
	}

	@Override
	public void onCreate(){
		initializePlayer();
		Log.d("mpservice", "on create reached");

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY; 
	}
	@Override
	public void onDestroy(){
		if (mediaPlayer != null){
		mediaPlayer.release();		
		mediaPlayer = null;
	}
		
	}

	public void initializePlayer(){
		Log.d("mpservice", ""+ this + "sound file " + R.raw.sleeping_pill);
		mediaPlayer = MediaPlayer.create(MediaPlayerService.this, R.raw.sleeping_pill);                
		Log.d("mpservice", "player created");
		mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);


	}
}
