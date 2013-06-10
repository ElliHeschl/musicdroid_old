package at.tugraz.musicdroid.drums;

import java.util.ArrayList;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import at.tugraz.musicdroid.DrumsActivity;
import at.tugraz.musicdroid.R;

public class DrumsLayoutManager {
	private Context context = null;
	private RelativeLayout soundRowBox = null; 
	private int localId = 5010;
	private ArrayList<DrumSoundRow> drumSoundRowsArray;
	
	public DrumsLayoutManager(Context c)
	{
		this.context = c;
		this.soundRowBox = (RelativeLayout) ((DrumsActivity)context).findViewById(R.id.drums_drum_row_box);
		this.drumSoundRowsArray = new ArrayList<DrumSoundRow>();
        initializeDrumSoundRows();
	}
	
	
	private void initializeDrumSoundRows() 
	{
		DrumSoundRow baseDrum = new DrumSoundRow(context, R.string.base_drum, R.raw.base_drum);
		baseDrum.setId(getNewId());
		LayoutParams layoutParamsBase = (LayoutParams) baseDrum.getLayoutParams();
		layoutParamsBase.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		soundRowBox.addView(baseDrum, layoutParamsBase);
		drumSoundRowsArray.add(baseDrum);
		((DrumsActivity)context).addObserverToEventHandler(baseDrum);
		
		DrumSoundRow snareDrum = new DrumSoundRow(context, R.string.snare_drum, R.raw.snare_drum);
		snareDrum.setId(getNewId());
		LayoutParams layoutParamsSnare = (LayoutParams) snareDrum.getLayoutParams();
		layoutParamsSnare.addRule(RelativeLayout.BELOW, baseDrum.getId());
		soundRowBox.addView(snareDrum, layoutParamsSnare);
		drumSoundRowsArray.add(snareDrum);
		((DrumsActivity)context).addObserverToEventHandler(snareDrum);
		
		DrumSoundRow highHatClosed = new DrumSoundRow(context, R.string.high_hat_closed, R.raw.high_hat_closed);
		highHatClosed.setId(getNewId());
		LayoutParams layoutParamsHighHatClosed = (LayoutParams) highHatClosed.getLayoutParams();
		layoutParamsHighHatClosed.addRule(RelativeLayout.BELOW, snareDrum.getId());
		soundRowBox.addView(highHatClosed, layoutParamsHighHatClosed);
		drumSoundRowsArray.add(highHatClosed);
		((DrumsActivity)context).addObserverToEventHandler(highHatClosed);
		
		DrumSoundRow highHatOpen = new DrumSoundRow(context, R.string.high_hat_open, R.raw.high_hat_open);
		highHatOpen.setId(getNewId());
		LayoutParams layoutParamsHighHatOpen = (LayoutParams) highHatOpen.getLayoutParams();
		layoutParamsHighHatOpen.addRule(RelativeLayout.BELOW, highHatClosed.getId());
		soundRowBox.addView(highHatOpen, layoutParamsHighHatOpen);
		drumSoundRowsArray.add(highHatOpen);
		((DrumsActivity)context).addObserverToEventHandler(highHatOpen);
		
		DrumSoundRow highTom = new DrumSoundRow(context, R.string.high_tom, R.raw.tom_high);
		highTom.setId(getNewId());
		LayoutParams layoutParamsHighTom = (LayoutParams) highTom.getLayoutParams();
		layoutParamsHighTom.addRule(RelativeLayout.BELOW, highHatOpen.getId());
		soundRowBox.addView(highTom, layoutParamsHighTom);
		drumSoundRowsArray.add(highTom);
		((DrumsActivity)context).addObserverToEventHandler(highTom);
		
		DrumSoundRow lowTom = new DrumSoundRow(context, R.string.low_tom, R.raw.tom_low);
		lowTom.setId(getNewId());
		LayoutParams layoutParamsLowTom = (LayoutParams) lowTom.getLayoutParams();
		layoutParamsLowTom.addRule(RelativeLayout.BELOW, highTom.getId());
		layoutParamsLowTom.bottomMargin = 0;
		lowTom.setLayoutParams(layoutParamsLowTom);
		soundRowBox.addView(lowTom, layoutParamsLowTom);
		drumSoundRowsArray.add(lowTom);
		((DrumsActivity)context).addObserverToEventHandler(lowTom);
	}
	
	private int getNewId()
	{
		localId = localId + 1;
		return localId;
	}
	
	public ArrayList<DrumSoundRow> getDrumSoundRowsArray()
	{
		return drumSoundRowsArray;
	}
}
