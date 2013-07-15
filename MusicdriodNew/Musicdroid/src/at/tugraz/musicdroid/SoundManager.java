package at.tugraz.musicdroid;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager {
	 
    static private SoundManager _instance;
    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundPoolMap;
    private static HashMap<Integer, Integer> soundPlayMap;
    private static AudioManager  audioManager;
    private static Context context;

    private SoundManager(){}


    static synchronized public SoundManager getInstance()
    {
        if (_instance == null)
          _instance = new SoundManager();
        return _instance;
     }


    public  void initSounds(Context theContext)
    {
         context = theContext;
         soundPool = new SoundPool(400, AudioManager.STREAM_MUSIC, 0);
         soundPoolMap = new HashMap<Integer, Integer>();
         soundPlayMap = new HashMap<Integer, Integer>();
         audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
    } 

    public void addSound(int Index,int SoundID)
    {
            soundPoolMap.put(Index, soundPool.load(context, SoundID, 1));
    }

    public int addSoundByPath(String path)
    {
    	int soundId = soundPool.load(path,1);
    	int position = soundPoolMap.size()+1;
    	soundPoolMap.put(position, soundId);
    	return position;
    }
    
    public int loadSound(int raw_id)
    {
    	int position = soundPoolMap.size()+1;
    	soundPoolMap.put(position, soundPool.load(context, raw_id, 1));
    	return position;
    }    

    public void loadSounds()
    {
            soundPoolMap.put(soundPlayMap.size()+1, soundPool.load(context, R.raw.test_midi, 1));
            soundPoolMap.put(soundPlayMap.size()+1, soundPool.load(context, R.raw.test_wav, 1));
    }

    public void playSoundByRawId(int raw_id, float speed)
    {
        Iterator<Entry<Integer, Integer>> it = soundPoolMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Integer, Integer> pairs = (Entry<Integer, Integer>)it.next();
            if(pairs.getValue() == raw_id)
            {
              playSound(pairs.getKey(), speed, 1);
              return;
            }
        }
    }
    
    public void stopSoundByRawId(int raw_id)
    {
        Iterator<Entry<Integer, Integer>> it = soundPoolMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Integer, Integer> pairs = (Entry<Integer, Integer>)it.next();
            if(pairs.getValue() == raw_id)
            {
              stopSound(pairs.getKey());
              return;
            }
        }
    }

    public void playSound(int index, float speed, float volume)
    {
            float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            
            Log.i("SoundManager", "SoundPoolID = " + index);
            int poolId = soundPoolMap.get(index);
            Integer stream_id = soundPool.play(poolId, volume, volume, 1, 0, speed);
            Log.e("PUT: ", "" + index + " " + stream_id);
            soundPlayMap.put(index, stream_id);
    }
    
    public void playSingleSound(int index, float speed, float volume)
    {
            float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            
            Log.i("SoundManager", "SoundPoolID = " + index);
            int poolId = soundPoolMap.get(index);
            Integer stream_id = soundPool.play(poolId, volume, volume, 1, 0, speed);
    }

    
    public void stopAllSounds()
    {
        Iterator<Entry<Integer, Integer>> it = soundPlayMap.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry<Integer, Integer> pairs = (Entry<Integer, Integer>)it.next();
            soundPool.stop(pairs.getValue());
        }
        soundPlayMap.clear();
 
    }
    

    public void stopSound(int index)
    {
            soundPool.stop(soundPlayMap.get(index));
            soundPlayMap.remove(index);
    }

    
    public void cleanup()
    {
        soundPool.release();
        soundPool = null;
        soundPoolMap.clear();
        audioManager.unloadSoundEffects();
        _instance = null;
    }
    
    
    public int getSoundfileDuration(int soundfile_id)
    {
		MediaPlayer player = MediaPlayer.create(context, soundfile_id);
		int duration = player.getDuration();
		return duration/1000; 	
    }
    
    public  int getSoundfileDurationByPath(String path)
    {
		Log.i("Player", "playRecorderFile");
		MediaPlayer player = new MediaPlayer();
		
		try {
			player.setDataSource(path);
			player.prepare();
		} catch (Exception e) {
			Log.i("Player-Exception", "Exception");
			e.printStackTrace();
		}

		return player.getDuration()/1000;

    }

}