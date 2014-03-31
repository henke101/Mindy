package se.chalmers.mindy.view;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.mindy.R;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
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
	private MediaPlayer mediaPlayer;

	public SoundIndexListItem(Context context, String name, String description) {
		super(context, name, description);

		audioContentResId = 0;
	}

	public SoundIndexListItem(Context context, String name, String description, int audioContentResId) {
		super(context, name, description);

		this.audioContentResId = audioContentResId;

		mediaPlayer = MediaPlayer.create(context, audioContentResId);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

	}

	@Override
	public List<View> getSubviews() {
		List<View> subviews = new ArrayList<View>();

		Context context = getContext();

		int holoPurpleBright = context.getResources().getColor(android.R.color.holo_purple);
		Typeface robotoLightCondensed = Typeface.createFromAsset(context.getAssets(), "fonts/roboto_condensed_light.ttf");

		float weight = 1.0f / 2.0f;
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, weight);

		final Button playButton = new Button(context);
		final Button pauseButton = new Button(context);
		final Button stopButton = new Button(context);

		playButton.setText(R.string.index_play);
		playButton.setBackgroundResource(android.R.drawable.list_selector_background);
		playButton.setTextColor(holoPurpleBright);
		playButton.setTypeface(robotoLightCondensed);
		playButton.setLayoutParams(param);
		playButton.setFocusable(false);

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.start();
				playButton.setVisibility(View.GONE);
				pauseButton.setVisibility(View.VISIBLE);
				stopButton.setVisibility(View.VISIBLE);
			}
		});

		subviews.add(playButton);

		pauseButton.setText(R.string.index_pause);
		pauseButton.setBackgroundResource(android.R.drawable.list_selector_background);
		pauseButton.setTextColor(holoPurpleBright);
		pauseButton.setTypeface(robotoLightCondensed);
		pauseButton.setLayoutParams(param);
		pauseButton.setFocusable(false);
		pauseButton.setVisibility(View.GONE);

		pauseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.pause();
				}
				pauseButton.setVisibility(View.GONE);
				playButton.setVisibility(View.VISIBLE);
			}
		});

		subviews.add(pauseButton);

		stopButton.setText(R.string.index_stop);
		stopButton.setBackgroundResource(android.R.drawable.list_selector_background);
		stopButton.setTextColor(holoPurpleBright);
		stopButton.setTypeface(robotoLightCondensed);
		stopButton.setLayoutParams(param);
		stopButton.setFocusable(false);
		stopButton.setVisibility(View.GONE);

		stopButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mediaPlayer.stop();
				mediaPlayer.prepareAsync();

				pauseButton.setVisibility(View.GONE);
				stopButton.setVisibility(View.GONE);
				playButton.setVisibility(View.VISIBLE);

			}
		});

		subviews.add(stopButton);

		return subviews;
	}
}
