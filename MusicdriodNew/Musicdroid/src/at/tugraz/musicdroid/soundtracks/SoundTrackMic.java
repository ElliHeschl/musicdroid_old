package at.tugraz.musicdroid.soundtracks;

import java.util.Observable;

import android.util.Log;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.types.SoundType;

public class SoundTrackMic extends SoundTrack {
	
	public SoundTrackMic() {
		type = SoundType.MIC;
		name = "SoundfileMic";
		soundfileRawId = R.raw.test_wav;
		duration =  SoundManager.getInstance().getSoundfileDuration(soundfileRawId);
		soundpoolId = SoundManager.getInstance().loadSound(soundfileRawId);
		Log.i("SoundTrackMIC", "SoundpoolID = " + soundpoolId);
	}
	
	public SoundTrackMic(String path) {
		type = SoundType.MIC;
		name = Helper.getInstance().getFilenameFromPath(path);
		soundpoolId = SoundManager.getInstance().addSoundByPath(path);
		duration = SoundManager.getInstance().getSoundfileDurationByPath(path);		
	}
	
	public SoundTrackMic(SoundTrackMic stm)
	{
		super(stm);
		name = stm.name;
		soundpoolId = stm.soundpoolId;
		duration = stm.duration;
	}
	
	 
		@Override
		public void update(Observable observable, Object data) {
			int cur_time = (Integer)data;
			Log.i("Incoming ObjectMic: ", "" + cur_time);
			if(cur_time == startPoint)
			{
				Log.i("SoundTrackMic ", "PlaySound");
				SoundManager.getInstance().playSound(soundpoolId, 1, volume);
			}
		}

}
