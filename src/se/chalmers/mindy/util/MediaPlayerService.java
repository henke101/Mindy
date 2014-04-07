package se.chalmers.mindy.util;


import java.io.FileDescriptor;



import se.chalmers.mindy.R;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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
		playPausePressed();
		Log.d("mpservice", "on create reched");

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY; 
	}

	public void playPausePressed(){
		if (mediaPlayer != null && mediaPlayer.isPlaying()){
			mediaPlayer.pause();
		}
		else{ 

			if (mediaPlayer == null){
				Log.d("mpservice", ""+ this + "sound file " + R.raw.sleeping_pill);
				mediaPlayer = MediaPlayer.create(MediaPlayerService.this, R.raw.sample_soundfile);                
				Log.d("mpservice", "player created");
			}
			mediaPlayer.start();
			Log.d("mpservice", "player started");
			mediaPlayer.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
		}

	}
}
