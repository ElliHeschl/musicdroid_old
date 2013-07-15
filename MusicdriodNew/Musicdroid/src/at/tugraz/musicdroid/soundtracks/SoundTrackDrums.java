package at.tugraz.musicdroid.soundtracks;

import java.util.ArrayList;
import java.util.Observable;

import android.util.Log;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.drums.DrumLoopEventHandler;
import at.tugraz.musicdroid.drums.DrumPreset;
import at.tugraz.musicdroid.drums.DrumPresetHandler;
import at.tugraz.musicdroid.drums.DrumSoundRow;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.preferences.PreferenceManager;
import at.tugraz.musicdroid.types.SoundType;

public class SoundTrackDrums extends SoundTrack {
	private DrumLoopEventHandler eventHandler = null;
	private DrumPreset preset = null;
	private String path = null;
	
	public SoundTrackDrums() {
		type = SoundType.DRUMS;
		name = "SoundfileDrums";
		soundfileRawId = R.raw.test_midi;
		duration =  SoundManager.getInstance().getSoundfileDuration(soundfileRawId);
		soundpoolId = SoundManager.getInstance().loadSound(soundfileRawId);
	}
	public SoundTrackDrums(SoundTrackDrums stm)
	{
		super(stm);
		Log.e("Calling copy constr", "drums");
		path = stm.path;
		DrumPresetHandler presetHandler = new DrumPresetHandler();
		preset = presetHandler.readPresetByName(path);
		eventHandler = new DrumLoopEventHandler();
		eventHandler.setLoops(stm.getEventHandler().getLoops());
		
		ArrayList<DrumSoundRow> drumRows = preset.getDrumSoundRowsArray();
		for(int i = 0; i < drumRows.size(); i++)
		{
			eventHandler.addObserver(drumRows.get(i));
		}
	}
	
	public SoundTrackDrums(String path, int loops) {
		type = SoundType.DRUMS;
		name = path;
		this.path = path;
		//soundpoolId = SoundManager.addSoundByPath(path);
		duration = 4*loops*60/PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_BPM_KEY);
		DrumPresetHandler presetHandler = new DrumPresetHandler();
		preset = presetHandler.readPresetByName(path);
		eventHandler = new DrumLoopEventHandler();
		eventHandler.setLoops(loops);
		
		ArrayList<DrumSoundRow> drumRows = preset.getDrumSoundRowsArray();
		for(int i = 0; i < drumRows.size(); i++)
		{
			eventHandler.addObserver(drumRows.get(i));
		}
	}
	
	@Override
	public void update(Observable observable, Object data) {
		int cur_time = (Integer)data;
		//Log.i("Incoming ObjectDrums: ", "Current Time " + cur_time + "  Start Time " + startPoint);
		if(cur_time == startPoint)
		{
			eventHandler.play();
		}
		if(cur_time < 0)
		{
			eventHandler.stop();
		}
	}
	
	public void startLoop()
	{
		eventHandler.play();
	}
	
	public void stopLoop()
	{
		eventHandler.stop();
	}
	
	public String getPath()
	{
		return path;
	}
	
	public DrumLoopEventHandler getEventHandler()
	{
		return eventHandler;
	}

	public void updateAfterEdit(String path, int loops) {
		DrumPresetHandler presetHandler = new DrumPresetHandler();
		duration = 4*loops*60/PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_BPM_KEY);
		preset = presetHandler.readPresetByName(path);
		eventHandler = new DrumLoopEventHandler();
		eventHandler.setLoops(loops);
		
		ArrayList<DrumSoundRow> drumRows = preset.getDrumSoundRowsArray();
		for(int i = 0; i < drumRows.size(); i++)
		{
			eventHandler.addObserver(drumRows.get(i));
		}
	}
}
