package se.chalmers.mindy.view;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import se.chalmers.mindy.util.MediaPlayerService;
import se.chalmers.mindy.util.MediaPlayerService.MyLocalBinder;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Class used for audio views in index page. Takes a resource id for the sound file and plays this sound file when the user presses Play
 * 
 * @author Viktor Åkerskog
 *
 */
public class SoundIndexListItem extends IndexListItem {

	private int audioContentResId;
	private Fragment fragment;
	private MediaPlayerService mMediaPlayerService;

	public SoundIndexListItem(Context context, int nameResId, int descriptionResId, int audioContentResId, Fragment fragment) {
		super(context, context.getResources().getString(nameResId), context.getResources().getString(descriptionResId));

		this.audioContentResId = audioContentResId;

		this.fragment = fragment;

		Bundle bundle = new Bundle();
		bundle.putInt(Constants.MEDIA_AUDIO_ID, audioContentResId);
		bundle.putInt(Constants.MEDIA_TITLE_ID, nameResId);
		bundle.putInt(Constants.MEDIA_INFO_ID, descriptionResId);
		this.fragment.setArguments(bundle);
	}

	@Override
	public List<View> getSubviews() {
		List<View> subviews = new ArrayList<View>();

		final Context context = getContext();

		final Button playButton = new Button(context);
		final Button pauseButton = new Button(context);
		final Button stopButton = new Button(context);

		playButton.setText(R.string.index_play);
		setViewAttributes(playButton);

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(new Intent(context.getApplicationContext(), MediaPlayerService.class));
				intent.putExtra(Constants.MEDIA_AUDIO_ID, audioContentResId);
				context.bindService(intent, mpConnection, Context.BIND_AUTO_CREATE);
				context.startService(intent);

				playButton.setVisibility(View.GONE);
				pauseButton.setVisibility(View.VISIBLE);
				stopButton.setVisibility(View.VISIBLE);
			}
		});

		subviews.add(playButton);

		pauseButton.setText(R.string.index_pause);
		setViewAttributes(pauseButton);
		pauseButton.setVisibility(View.GONE);

		pauseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMediaPlayerService.pausePlayback();

				pauseButton.setVisibility(View.GONE);
				playButton.setVisibility(View.VISIBLE);
			}
		});

		subviews.add(pauseButton);

		stopButton.setText(R.string.index_stop);
		setViewAttributes(stopButton);
		stopButton.setVisibility(View.GONE);

		stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMediaPlayerService.stopPlayback();

				pauseButton.setVisibility(View.GONE);
				stopButton.setVisibility(View.GONE);
				playButton.setVisibility(View.VISIBLE);

			}
		});

		subviews.add(stopButton);

		return subviews;
	}

	public void setViewAttributes(final Button button) {
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f / 2.0f);

		button.setBackgroundResource(R.drawable.selector_button);
		button.setTextColor(context.getResources().getColorStateList(R.drawable.selector_button_text));
		button.setTypeface(robotoLightCondensed);
		button.setLayoutParams(param);
		button.setFocusable(false);
	}

	@Override
	public OnClickListener getTitleOnClickListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (context instanceof MainActivity) {
					Bundle extras = fragment.getArguments();
					extras.putInt(Constants.MEDIA_PROGRESS, mMediaPlayerService.getPlaybackPosition());

					fragment.setArguments(extras);
					mMediaPlayerService.pausePlayback();
					((MainActivity) context).setFragment(fragment);

				}
			}
		};

	}

	private ServiceConnection mpConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			MyLocalBinder binder = (MyLocalBinder) service;
			mMediaPlayerService = binder.getService();
			if (mMediaPlayerService != null) {
				mMediaPlayerService.startPlayback();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
		}

	};

}
