package at.tugraz.musicdroid.drums;

import java.util.Observable;
import java.util.Observer;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;

@Root
public class DrumSoundRow implements Observer {
	private Context context = null;
	private DrumSoundRowLayout layout = null;
	@ElementArray
	private int[] beatArray = {0,0,0,0,0,0,0,0};
	@Element
	private int rawId = 0;
	@Element
	private int soundPoolId = 0;
	
	public DrumSoundRow()
	{
		
	}
	
	public DrumSoundRow(Context context, int rowStringId, int soundRawId) {
		//super(context);
		this.context = context;
		this.rawId = soundRawId;
		
		layout = new DrumSoundRowLayout(this.context, this, rowStringId);
		
        soundPoolId = SoundManager.loadSound(soundRawId);
	}

	public void togglePosition(int position)
	{
		beatArray[position] = beatArray[position] == 0 ? 1 : 0;
	}
	
	public int getBeatArrayValueAtPosition(int position)
	{
		return beatArray[position];
	}
	
	@Override
	public void update(Observable observable, Object data) {
		int currentBeat = (Integer)data;
		//Log.i("DrumSoundRow", "Incoming Object: " + currentBeat);
		if(beatArray[currentBeat] == 1)
		{
			SoundManager.playSound(soundPoolId, 1, 1);
		}
	}
	
	public int getSoundPoolId()
	{
		return soundPoolId;
	}
	
	public int[] getBeatArray()
	{
		return beatArray;
	}
	
	public void setBeatArray(int[] beatArray)
	{
		this.beatArray = beatArray;
		layout.updateOnPresetLoad(this.beatArray);
	}
	
	public void setSoundPoolId(int spId)
	{
		soundPoolId =  spId;
	}
	
	public DrumSoundRowLayout getLayout()
	{
		return layout;
	}
}
