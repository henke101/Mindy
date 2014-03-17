package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SleepingPillFragment extends Fragment {
	MediaPlayer mediaPlayer;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		View view = inflater.inflate(R.layout.fragment_sleepingpill, null);

		mediaPlayer = MediaPlayer.create(getActivity(), R.raw.sample_soundfile);
		//mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);
		
		IntentFilter mediaFilter = new IntentFilter(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY);
		mediaFilter.setPriority(1000);
		getActivity().getApplicationContext().registerReceiver(audioIntentReceiver, mediaFilter);
		

		mediaPlayer.start();

		return view;
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if (mediaPlayer != null){
			mediaPlayer.release();		
			mediaPlayer = null;
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
		    	 // Send an intent to MainActivity
		    	 // MainActivity can interact with SleepingPillFragment since MainActivity contains SleepingPillFragment
		    	 // Get SleepingPillFragment to stop playing music(by running its onStop method, for instance)
		    	 //or..... get the sleepingpillfragment and run onPause()
		      }
		   }
	};
}

