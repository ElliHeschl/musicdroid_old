package at.tugraz.musicdroid.drums;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
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
		DrumSoundRowLayout baseDrumLayout = baseDrum.getLayout();
		baseDrumLayout.setId(getNewId());
		LayoutParams layoutParamsBase = (LayoutParams) baseDrumLayout.getLayoutParams();
		layoutParamsBase.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		soundRowBox.addView(baseDrumLayout, layoutParamsBase);
		drumSoundRowsArray.add(baseDrum);
		((DrumsActivity)context).addObserverToEventHandler(baseDrum);
		
		DrumSoundRow snareDrum = new DrumSoundRow(context, R.string.snare_drum, R.raw.snare_drum);
		DrumSoundRowLayout snareDrumLayout = snareDrum.getLayout();
		snareDrumLayout.setId(getNewId());
		LayoutParams layoutParamsSnare = (LayoutParams) snareDrumLayout.getLayoutParams();
		layoutParamsSnare.addRule(RelativeLayout.BELOW, baseDrumLayout.getId());
		soundRowBox.addView(snareDrumLayout, layoutParamsSnare);
		drumSoundRowsArray.add(snareDrum);
		((DrumsActivity)context).addObserverToEventHandler(snareDrum);
		
		DrumSoundRow highHatClosed = new DrumSoundRow(context, R.string.high_hat_closed, R.raw.high_hat_closed);
		DrumSoundRowLayout highHatClosedLayout = highHatClosed.getLayout();
		highHatClosedLayout.setId(getNewId());
		LayoutParams layoutParamsHighHatClosed = (LayoutParams) highHatClosedLayout.getLayoutParams();
		layoutParamsHighHatClosed.addRule(RelativeLayout.BELOW, snareDrumLayout.getId());
		soundRowBox.addView(highHatClosedLayout, layoutParamsHighHatClosed);
		drumSoundRowsArray.add(highHatClosed);
		((DrumsActivity)context).addObserverToEventHandler(highHatClosed);
		
		DrumSoundRow highHatOpen = new DrumSoundRow(context, R.string.high_hat_open, R.raw.high_hat_open);
		DrumSoundRowLayout highHatOpenLayout = highHatOpen.getLayout();
		highHatOpenLayout.setId(getNewId());
		LayoutParams layoutParamsHighHatOpen = (LayoutParams) highHatOpenLayout.getLayoutParams();
		layoutParamsHighHatOpen.addRule(RelativeLayout.BELOW, highHatClosedLayout.getId());
		soundRowBox.addView(highHatOpenLayout, layoutParamsHighHatOpen);
		drumSoundRowsArray.add(highHatOpen);
		((DrumsActivity)context).addObserverToEventHandler(highHatOpen);
		
		DrumSoundRow highTom = new DrumSoundRow(context, R.string.high_tom, R.raw.tom_high);
		DrumSoundRowLayout highTomLayout = highTom.getLayout();
		highTomLayout.setId(getNewId());
		LayoutParams layoutParamsHighTom = (LayoutParams) highTomLayout.getLayoutParams();
		layoutParamsHighTom.addRule(RelativeLayout.BELOW, highHatOpenLayout.getId());
		soundRowBox.addView(highTomLayout, layoutParamsHighTom);
		drumSoundRowsArray.add(highTom);
		((DrumsActivity)context).addObserverToEventHandler(highTom);
		
		DrumSoundRow lowTom = new DrumSoundRow(context, R.string.low_tom, R.raw.tom_low);
		DrumSoundRowLayout lowTomLayout = lowTom.getLayout();
		lowTomLayout.setId(getNewId());
		LayoutParams layoutParamsLowTom = (LayoutParams) lowTomLayout.getLayoutParams();
		layoutParamsLowTom.addRule(RelativeLayout.BELOW, highTomLayout.getId());
		layoutParamsLowTom.bottomMargin = 0;
		lowTomLayout.setLayoutParams(layoutParamsLowTom);
		soundRowBox.addView(lowTomLayout, layoutParamsLowTom);
		drumSoundRowsArray.add(lowTom);
		((DrumsActivity)context).addObserverToEventHandler(lowTom);
	}
	
	public void loadPresetToDrumLayout(DrumPreset preset)
	{
		Log.i("DrumsLayoutManager", "loadPresetToDrumLayout");
		if(preset.getDrumSoundRowsArray().size() != drumSoundRowsArray.size())
		{
			Log.e("DrumsLayoutManager", "Error at loading preset: to few/much drum rows");
		}
		for(int i = 0; i < drumSoundRowsArray.size(); i++)
		{
		    DrumSoundRow r = preset.getDrumSoundRowsArray().get(i);
		    drumSoundRowsArray.get(i).setSoundPoolId(r.getSoundPoolId());
		    drumSoundRowsArray.get(i).setBeatArray(r.getBeatArray());
		}
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
