package se.chalmers.mindy.util;



public class SoundHandler {
	
	private static boolean soundIsPlaying;
	
	public static boolean getSoundIsPlaying(){
		return soundIsPlaying;
	}
	
	public static void setSoundIsPlaying(Boolean b){
		soundIsPlaying = b;
	}
}
