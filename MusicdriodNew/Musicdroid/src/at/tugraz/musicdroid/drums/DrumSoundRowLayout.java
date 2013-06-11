package at.tugraz.musicdroid.drums;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;

public class DrumSoundRowLayout extends RelativeLayout implements OnClickListener{
	private DrumSoundRow drumSoundRow = null;
	private Context context = null;
	private int rowStringId;
	private TextView drumRowText = null;
		
	public DrumSoundRowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DrumSoundRowLayout(Context context, DrumSoundRow dsr, int rowStringId) {
		super(context);
		this.context = context;
		this.drumSoundRow = dsr;
		this.rowStringId = rowStringId;
		

        LayoutInflater inflater = LayoutInflater.from(this.context);
        inflater.inflate(R.layout.drum_sound_row_layout, this);

        initDrumSoundRow(rowStringId);
		// TODO Auto-generated constructor stub
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

	public void updateOnPresetLoad(int[] beatArray)
	{
		Log.i("DrumSoundRowLayout", "BeatArray = " + beatArray[0] + " " + beatArray[1] + " " + beatArray[2]);
		((DrumButton) findViewById(R.id.drum_button_1_1)).changeDrawableOnClick(beatArray[0]);
		((DrumButton) findViewById(R.id.drum_button_1_2)).changeDrawableOnClick(beatArray[1]);
		((DrumButton) findViewById(R.id.drum_button_1_3)).changeDrawableOnClick(beatArray[2]);
		((DrumButton) findViewById(R.id.drum_button_1_4)).changeDrawableOnClick(beatArray[3]);
		((DrumButton) findViewById(R.id.drum_button_2_1)).changeDrawableOnClick(beatArray[4]);
		((DrumButton) findViewById(R.id.drum_button_2_2)).changeDrawableOnClick(beatArray[5]);
		((DrumButton) findViewById(R.id.drum_button_2_3)).changeDrawableOnClick(beatArray[6]);
		((DrumButton) findViewById(R.id.drum_button_2_4)).changeDrawableOnClick(beatArray[7]);
	}
	
	@Override
	public void onClick(View v) {
		if(v instanceof DrumButton)
		{
			int position = ((DrumButton)v).getPosition();
			drumSoundRow.togglePosition(position-1);
			((DrumButton)v).changeDrawableOnClick(drumSoundRow.getBeatArrayValueAtPosition(position-1));
		}	
		else if(v.getId() == ((RelativeLayout) findViewById(R.id.drum_row_descriptor_box)).getId())
		{
			SoundManager.playSound(drumSoundRow.getSoundPoolId(), 1, 1);
		}
	}


}
