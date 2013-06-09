package at.tugraz.musicdroid.drums;

import java.util.Observable;
import java.util.Observer;

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

public class DrumSoundRow extends RelativeLayout implements OnClickListener, Observer {
	private Context context = null;
	private TextView drumRowText = null;
	private int[] beatArray = {0,0,0,0,0,0,0,0};
	private int soundpoolId = 0;
	
	public DrumSoundRow(Context context, int rowStringId, int soundRawId) {
		super(context);
		this.context = context;
		
        LayoutInflater inflater = LayoutInflater.from(this.context);
        inflater.inflate(R.layout.drum_sound_row_layout, this);
        soundpoolId = SoundManager.loadSound(soundRawId);
        initDrumSoundRow(rowStringId);
	}
	
	public DrumSoundRow(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	private void initDrumSoundRow(int rowStringId)
	{
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setLayoutParams(layoutParams);
		drumRowText = (TextView) findViewById(R.id.drum_row_text);
		drumRowText.setText(rowStringId);
		setDrumButtonOnClickListener();
	}
	
	private void setDrumButtonOnClickListener()
	{
		((DrumButton) findViewById(R.id.drum_button_1_1)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_1_2)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_1_3)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_1_4)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_2_1)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_2_2)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_2_3)).setOnClickListener(this);
		((DrumButton) findViewById(R.id.drum_button_2_4)).setOnClickListener(this);
		((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v instanceof DrumButton)
		{
			int position = ((DrumButton)v).getPosition();
			beatArray[position-1] = beatArray[position-1] == 0 ? 1 : 0;
			((DrumButton)v).changeDrawableOnClick(beatArray[position-1]);
		}	
		else if(v.getId() == ((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).getId())
		{
			SoundManager.playSound(soundpoolId, 1, 1);
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		int currentBeat = (Integer)data;
		Log.i("DrumSoundRow", "Incoming Object: " + currentBeat);
		if(beatArray[currentBeat] == 1)
		{
			SoundManager.playSound(soundpoolId, 1, 1);
		}
	}
}
