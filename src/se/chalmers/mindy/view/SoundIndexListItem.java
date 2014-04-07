package se.chalmers.mindy.view;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.mindy.R;
import se.chalmers.mindy.core.MainActivity;
import android.app.Fragment;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
	private MediaPlayer mediaPlayer;
	private Fragment fragment;

	public SoundIndexListItem(Context context, int nameResId, int descriptionResId, int audioContentResId, Fragment fragment) {
		this(context, context.getResources().getString(nameResId), context.getResources().getString(descriptionResId), audioContentResId, fragment);

	}

	// context.getResources().getString(nameResId), context.getResources().getString(descriptionResId)
	public SoundIndexListItem(Context context, String name, String description, int audioContentResId, Fragment fragment) {
		super(context, name, description);

		this.audioContentResId = audioContentResId;

		mediaPlayer = MediaPlayer.create(context, audioContentResId);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

		this.fragment = fragment;

	}

	@Override
	public List<View> getSubviews() {
		List<View> subviews = new ArrayList<View>();

		Context context = getContext();

		float weight = 1.0f / 2.0f;

		final Button playButton = new Button(context);
		final Button pauseButton = new Button(context);
		final Button stopButton = new Button(context);

		playButton.setText(R.string.index_play);
		setViewAttributes(playButton);

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
		setViewAttributes(pauseButton);
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
		setViewAttributes(stopButton);
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
					((MainActivity) context).setFragment(fragment);
				}
			}
		};

	}
}
