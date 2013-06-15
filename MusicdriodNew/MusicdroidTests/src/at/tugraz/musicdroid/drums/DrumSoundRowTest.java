package at.tugraz.musicdroid.drums;

import java.io.File;
import java.util.ArrayList;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.DrumsActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.types.SoundType;

public class DrumSoundRowTest  extends ActivityInstrumentationTestCase2<MainActivity>{
	protected Solo solo = null;
	DrumsLayoutManager layoutManager = null;
	DrumSoundRow row = null;
	DrumSoundRowLayout layout = null;
	
	public DrumSoundRowTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 solo.clickOnView(getActivity().findViewById(R.id.btn_add));
		 solo.waitForText(solo.getString(R.string.dialog_add_sound_title), 1, 10000, true);
	     solo.sleep(100);
	     solo.clickOnText(solo.getString(SoundType.DRUMS.getNameResource()));
	     solo.sleep(2000);
	     

		 layoutManager = ((DrumsActivity)solo.getCurrentActivity()).getDrumsLayoutManager();
		 row = layoutManager.getDrumSoundRowsArray().get(0);
		 layout = row.getLayout();
	}

	
	public void testBeatButtonChangesOnClick()
	{	
		assertTrue(row != null);
		
		DrumButton testButton = (DrumButton)layout.findViewById(R.id.drum_button_1_2);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_unclicked_dark).getConstantState()));
		solo.clickOnView(testButton);
		solo.sleep(1000);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_clicked).getConstantState()));
		solo.clickOnView(testButton);
		solo.sleep(1000);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_unclicked_dark).getConstantState()));
				
	}
	
	
	public void testClearButton()
	{
		DrumButton testButton = (DrumButton)layout.findViewById(R.id.drum_button_1_2);
		solo.clickOnView(testButton);
		solo.sleep(1000);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_clicked).getConstantState()));

		View clear = solo.getView(R.id.drums_context_clear_preset);
		solo.clickOnView(clear);
		
		layoutManager = ((DrumsActivity)solo.getCurrentActivity()).getDrumsLayoutManager();
		row = layoutManager.getDrumSoundRowsArray().get(0);
		layout = row.getLayout();
		testButton = (DrumButton)layout.findViewById(R.id.drum_button_1_2);
		solo.clickOnView(testButton);
		solo.sleep(1000);
		assertTrue(
				testButton.getDrawable().getConstantState()
				.equals(getActivity().getResources().getDrawable(R.drawable.drum_button_unclicked_dark).getConstantState()));
	}
	
	
	
	public void testBeatArray()
	{
		int[] beatArray = row.getBeatArray();
		assertTrue(!contains(beatArray, 1));
		
		DrumButton testButton = (DrumButton)layout.findViewById(R.id.drum_button_1_2);
		solo.clickOnView(testButton);
		solo.sleep(1000);
		
		beatArray = row.getBeatArray();
		assertTrue(contains(beatArray, 1));
		
		solo.clickOnView(testButton);
		solo.sleep(1000);
		
		beatArray = row.getBeatArray();
		assertTrue(!contains(beatArray, 1));
	}
	
	
	private boolean contains(int[] array, int value)
	{
		for(int i = 0; i < array.length; i++)
		{
			if(array[i] == value)
				return true;
		}
		return false;
	}
	
}
