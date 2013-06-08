package at.tugraz.musicdroid.drums;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import at.tugraz.musicdroid.DrumsActivity;
import at.tugraz.musicdroid.R;

public class DrumsLayoutManager {
	private Context context = null;
	private RelativeLayout layout = null;
	private int localId = 5010;
	
	public DrumsLayoutManager(Context c)
	{
		this.context = c;
		this.layout = (RelativeLayout) ((DrumsActivity)context).findViewById(R.id.drums_activity_layout);
        initializeDrumSoundRows();
	}
	
	
	private void initializeDrumSoundRows() 
	{
		DrumSoundRow baseDrum = new DrumSoundRow(context, R.string.base_drum, R.raw.base_drum);
		baseDrum.setId(getNewId());
		LayoutParams layoutParamsBase = (LayoutParams) baseDrum.getLayoutParams();
		layoutParamsBase.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		layout.addView(baseDrum, layoutParamsBase);
		
		DrumSoundRow snareDrum = new DrumSoundRow(context, R.string.snare_drum, R.raw.snare_drum);
		snareDrum.setId(getNewId());
		LayoutParams layoutParamsSnare = (LayoutParams) snareDrum.getLayoutParams();
		layoutParamsSnare.addRule(RelativeLayout.BELOW, baseDrum.getId());
		layout.addView(snareDrum, layoutParamsSnare);
		
		DrumSoundRow highHatClosed = new DrumSoundRow(context, R.string.high_hat_closed, R.raw.high_hat_closed);
		highHatClosed.setId(getNewId());
		LayoutParams layoutParamsHighHatClosed = (LayoutParams) highHatClosed.getLayoutParams();
		layoutParamsHighHatClosed.addRule(RelativeLayout.BELOW, snareDrum.getId());
		layout.addView(highHatClosed, layoutParamsHighHatClosed);
		
		DrumSoundRow highHatOpen = new DrumSoundRow(context, R.string.high_hat_open, R.raw.high_hat_open);
		highHatOpen.setId(getNewId());
		LayoutParams layoutParamsHighHatOpen = (LayoutParams) highHatOpen.getLayoutParams();
		layoutParamsHighHatOpen.addRule(RelativeLayout.BELOW, highHatClosed.getId());
		layout.addView(highHatOpen, layoutParamsHighHatOpen);
		
		DrumSoundRow highTom = new DrumSoundRow(context, R.string.high_tom, R.raw.tom_high);
		highTom.setId(getNewId());
		LayoutParams layoutParamsHighTom = (LayoutParams) highTom.getLayoutParams();
		layoutParamsHighTom.addRule(RelativeLayout.BELOW, highHatOpen.getId());
		layout.addView(highTom, layoutParamsHighTom);
		
		DrumSoundRow lowTom = new DrumSoundRow(context, R.string.low_tom, R.raw.tom_low);
		lowTom.setId(getNewId());
		LayoutParams layoutParamsLowTom = (LayoutParams) lowTom.getLayoutParams();
		layoutParamsLowTom.addRule(RelativeLayout.BELOW, highTom.getId());
		layout.addView(lowTom, layoutParamsLowTom);
	}
	
	private int getNewId()
	{
		localId = localId + 1;
		return localId;
	}
}
