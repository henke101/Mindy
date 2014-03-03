package se.chalmers.mindy.util;

import android.content.Context;
import android.content.Intent;

public class AudioIntentReceiver extends android.content.BroadcastReceiver {
  
	@Override
   public void onReceive(Context ctx, Intent intent) {
      if (intent.getAction().equals(
                    android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
    	  
         // Send an intent to MainActivity
    	 // MainActivity can interact with SleepingPillFragment since MainActivity contains SleepingPillFragment
    	 // Get SleepingPillFragment to stop playing music(by running its onStop method, for instance)
      }
   }

}