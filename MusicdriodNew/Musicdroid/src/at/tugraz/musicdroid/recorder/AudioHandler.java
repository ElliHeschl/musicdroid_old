package at.tugraz.musicdroid.recorder;

import java.io.File;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.preferences.PreferenceManager;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class AudioHandler {
	public static AudioHandler instance = null;
	private RecorderLayout layout = null;
	private Context context = null;
	private String path = null;
	private String filename = null;
	private boolean init = false;
	private Recorder recorder = null;
	private Player player = null;
	private AudioVisualizer visualizer = null;
	
	private AudioHandler()
	{
		this.path = Environment.getExternalStorageDirectory().getAbsolutePath();
		this.filename = "test.mp3";
	}
	
	public static AudioHandler getInstance() {
        if (instance == null) {
            instance = new AudioHandler();
        }
        return instance;
    }
	
	public void init(Context context, RecorderLayout layout)
	{
		if(init) return;
		
		this.context = context;
		this.layout = layout;
		visualizer = new AudioVisualizer(context);
		recorder = new Recorder(context, layout, visualizer);
		player = new Player(layout);
		init = true;
	}
	
	public boolean startRecording()
	{
		Log.i("AudioHandler", "StartRecording");
		File check = new File(path+'/'+filename);
		if(check.exists())
		{
			showDialog();
		}
		else
		{
			checkAndStartPlaybackAndMetronom();
			recorder.record();
		}
		return true;
	}

	
	public void stopRecording()
	{
		recorder.stopRecording();
		if(PreferenceManager.getInstance().getPreference(PreferenceManager.PLAY_PLAYBACK_KEY) == 1)
			SoundMixer.getInstance().stopAllSoundsInSoundMixerAndRewind();
		else if(PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_VISUALIZATION_KEY) > 0)
			SoundMixer.getInstance().stopMetronom();
	}
	
	public void playRecordedFile()
	{
		player.playRecordedFile();
	}
	
	public void stopRecordedFile()
	{
		player.stopPlaying();		
	}
	
	private void showDialog()
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder
				.setMessage(R.string.dialog_warning_file_overwritten_at_record)
				.setCancelable(true)
				.setPositiveButton(R.string.dialog_abort,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								layout.resetLayoutToRecord();
							}
						})
				.setNegativeButton(R.string.dialog_continue,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								checkAndStartPlaybackAndMetronom();
								recorder.record();
							}
						});
		AlertDialog alertNewImage = alertDialogBuilder.create();
		alertNewImage.show();
	}
	
	private void checkAndStartPlaybackAndMetronom()
	{
		int metronom = PreferenceManager.getInstance().getPreference(PreferenceManager.METRONOM_VISUALIZATION_KEY); 
		if(PreferenceManager.getInstance().getPreference(PreferenceManager.PLAY_PLAYBACK_KEY) == 1)
		{
			AudioManager am1 = ((AudioManager)context.getSystemService(Context.AUDIO_SERVICE));
			if(!am1.isWiredHeadsetOn())
			{
				//Toast.makeText(context, "We recomend using your headphones when you record and use playback", Toast.LENGTH_LONG).show();
				if(!SoundMixer.getInstance().playAllSoundsInSoundmixer() && metronom > 0)
					SoundMixer.getInstance().startMetronom();
			}
			else
			{
			  if(!SoundMixer.getInstance().playAllSoundsInSoundmixer() && metronom > 0)
				SoundMixer.getInstance().startMetronom();
			}
		}
		else if(metronom > 0)
		{
			SoundMixer.getInstance().startMetronom();
		}
	}
	
	public void setFilename(String f)
	{
		this.filename = f;
		layout.updateFilename(Helper.getInstance().removeFileEnding(this.filename));
	}
	
	public String getFilenameFullPath()
	{
		return path + "/" + filename;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public void setContext(Context context)
	{
		this.context = context;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void reset()
	{
		init = false;
		instance = null;
	}
}
