package at.tugraz.musicdroid.drums;

import java.util.Observable;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.preferences.PreferenceManager;
import at.tugraz.musicdroid.soundmixer.SoundMixer;
import at.tugraz.musicdroid.soundmixer.timeline.TimelineEventHandler;

public class DrumLoopEventHandler extends Observable {
	private boolean shouldContinue;
	
	public DrumLoopEventHandler()
	{
	}
	
	public void play()
	{
		if(countObservers() > 0)
		{
			new Thread(new Runnable() {
		        @Override
		        public void run() {
		        	shouldContinue = true;
		        	int beat = 0;
		            while (shouldContinue) {
		                try {
		        			setChanged();
		        			notifyObservers(beat);
		        			beat = beat + 1;
		        			if(beat == 8) beat = 0;
		                    Thread.sleep(110);
		                } catch (Exception e) {
		                }
		            }
		            return;
		        }
		    }).start();
		}
	}
	
	public void stop()
	{
		shouldContinue = false;
	}

	
}
