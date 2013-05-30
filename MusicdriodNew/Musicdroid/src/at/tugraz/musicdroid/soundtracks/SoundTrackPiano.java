package at.tugraz.musicdroid.soundtracks;

import android.util.Log;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.types.SoundType;

public class SoundTrackPiano extends SoundTrack {
	
	public SoundTrackPiano() {
		type = SoundType.PIANO;
		name = "SoundfilePiano";
		soundfileRawId = R.raw.test_midi;
		duration =  SoundManager.getSoundfileDuration(soundfileRawId);
		soundpoolId = SoundManager.loadSound(soundfileRawId);
	}
	
	public SoundTrackPiano(SoundTrackPiano stm)
	{
		Log.e("Calling copy constr", "piano");
	}

}
