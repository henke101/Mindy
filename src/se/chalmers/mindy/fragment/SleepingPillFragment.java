package se.chalmers.mindy.fragment;

import se.chalmers.mindy.R;
import android.app.Fragment;
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
		View view = inflater.inflate(R.layout.fragment_sleepingpill, null);

		mediaPlayer = MediaPlayer.create(getActivity(), R.raw.sample_soundfile);
		//mediaPlayer.setWakeMode(getActivity(), PowerManager.PARTIAL_WAKE_LOCK);

		mediaPlayer.start();

		return view;
	}
	@Override
	public void onStop(){
		if (mediaPlayer != null){
			mediaPlayer.release();		
			mediaPlayer = null;
		}
	}
	@Override
	public void onDestroy(){
		if (mediaPlayer != null){
			mediaPlayer.release();		
			mediaPlayer = null;
		}
	}
}

