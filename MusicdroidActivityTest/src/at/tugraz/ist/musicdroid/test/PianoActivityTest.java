package at.tugraz.ist.musicdroid.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.easymock.internal.matchers.InstanceOf;

import android.app.Activity;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.Display;
import at.tugraz.ist.musicdroid.DrawTonesView;
import at.tugraz.ist.musicdroid.PianoActivity;
import at.tugraz.ist.musicdroid.Tone;
import at.tugraz.ist.musicdroid.common.Constants;

import com.jayway.android.robotium.solo.Solo;
//import org.catrobat.catroid.R;

public class PianoActivityTest extends ActivityInstrumentationTestCase2<PianoActivity> {
	
	private Solo solo;
	private PianoActivity pa;
	public PianoActivityTest() {
		super("at.tugraz.ist.musicdroid", PianoActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		pa = getActivity();
		solo = new Solo(getInstrumentation(), pa);
	}
	
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();
	}
    
	public void testDir(){
		File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"records"+File.separator+"piano_midi_sounds");
		assertTrue("File does not exist",directory.exists());
	}
	
	private String getMidiNote(int value){
		switch (value){
			case 0:
				return "C";
			case 1:
				return "Cis";
			case 2:
				return "D";
			case 3:
				return "Dis";
			case 4: 
				return "E";
			case 5:
			    return "F";
			case 6:
				return "Fis";
			case 7:
				return "G";
			case 8:
				return "Gis";
			case 9:
				return "A";
			case 10:
				return "Ais";
			case 11:
				return "H";
	
		}
		
		return "W";			
			
	}
	
	public void testMidiSound(){
		int counter = 0;
		String midi_note;
		String file_name;
		int midi_value = 0;
		for(midi_value = 36; midi_value < 96; midi_value++ ){
			midi_note = getMidiNote(counter);
			file_name = midi_note + "_" + midi_value + ".mid";
			File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"records"+File.separator+"piano_midi_sounds"+File.separator+file_name);
			assertTrue("File does not exist",directory.exists());
			counter++;
			if (counter == 12)
     			counter = 0;
		}
	}

	/**
	 * Test: testPianoMultipleKeys
	 * Test Piano functionality for multiple key, checks if Tone has been drawn 
	 */
	public void testPianoMultipleKeys() {
/*		for(int i = 0; i < 5; i++)
		  solo.clickOnView(solo.getView(pa.getResources().getIdentifier("piano", "id", getActivity().getPackageName())));
*/
		DrawTonesView tone_view = pa.getToneView(); //dirty
		assertTrue("Tone View is NULL", tone_view != null);
		assertTrue("Keys pressed before test started", tone_view == null || tone_view.getTonesSize() == 0);
		solo.clickLongOnScreen(400, 400);
		solo.sleep(500);
		solo.clickLongOnScreen(300, 400);
		solo.sleep(500);
		solo.clickLongOnScreen(200, 400);
		solo.sleep(500);
		assertTrue("No Key has been pressed", tone_view.getTonesSize() > 0); 
	}
	
	/**
	 * Test: testPianoSingleKey
	 * Test Piano functionality for a single key, checks if Tone has been drawn 
	 */
	public void testPianoSingleKey()
	{
		DrawTonesView tone_view = pa.getToneView(); //dirty
		assertTrue("Tone View is NULL", tone_view != null);
		assertTrue("Keys pressed before test started", tone_view == null || tone_view.getTonesSize() == 0);
		solo.clickOnView(solo.getView(getActivity().getResources().getIdentifier("piano", "id", getActivity().getPackageName())));
		assertTrue("No Key has been pressed", tone_view.getTonesSize() > 0);
	}
	
	/**
	 * Test: testPianoAudioSingleKey
	 * Tests sound output when pressing single key
	 */
	@UiThreadTest
	public void testPianoAudioSingleKey()
	{
		assertTrue("No Sound Played", pa.toggleSoundTest(60, true));	
	}
	
	/**
	 * Test: testPianoAudioMultipleKeys
	 * Tests sound output when pressing multiple keys 
	 */
	@UiThreadTest
	public void testPianoAudioMultipleKeys()
	{
		assertTrue("No Sound Played", pa.toggleSoundTest(60, true));
		solo.sleep(200);
		assertTrue("No Sound Played", pa.toggleSoundTest(65, true));
		solo.sleep(200);
		assertTrue("No Sound Played", pa.toggleSoundTest(55, true));	
	}
	
	
	/**
	 * Test: testPianoPlayAndStopSound
	 * Tests Play and Stop Function
	 */	
	public void testPianoPlayAndStopSound()
	{
		for(int i = 0; i < 10; i++)
		{
			solo.clickLongOnScreen(300+(i*10), 400, 50);
			solo.sleep(300);
		}
		solo.clickOnButton("Play");
		
		assertTrue("Sound is not playing", pa.isPlaying());
		
		solo.sleep(3000);
		
		solo.clickOnButton("Stop");
		
		assertFalse("Sound is still playing", pa.isPlaying());
		
		solo.sleep(2000);
		
	}
	
	/**
	 * Test: testPianoStopSoundAtReturn
	 * Tests if audio output stops after return to main menu
	 */
	public void testPianoStopSoundAtReturn()
	{
		for(int i = 0; i < 20; i++)
		{
			solo.clickOnScreen(300+(i*10), 400);
			solo.sleep(300);
		}
		solo.clickOnButton("Play");
		
		assertTrue("Sound is not playing", pa.isPlaying());
		
		solo.sleep(1000);
		
		solo.goBack();
		
		assertFalse("Sound is still playing", pa.isPlaying());
		
		solo.sleep(500);
	}
	
	/**
	 * Test: testPianoMultitouch()
	 * TODO: test multitouch piano input  
	 */
	public void testPianoMultitouch()
	{
		for(int i = 0; i < 20; i++)
		{
			solo.clickLongOnScreen(300, 400);
			solo.clickLongOnScreen(500, 300);
		}
	}
	
	public void testPianoScrollFunctionality()
	{
		DrawTonesView tone_view = pa.getToneView(); //dirty
		solo.clickLongOnScreen(400, 400, 50);
		solo.drag(400, 100, 400, 400, 40);
		solo.clickLongOnScreen(400, 400, 50);
		solo.drag(400, 800, 400, 400, 40);
		solo.drag(400, 800, 400, 400, 40);
		solo.sleep(800);
		solo.clickLongOnScreen(400, 400, 50);
		solo.sleep(1000);
		
		assertTrue("Tones View is null", tone_view != null);
		List<Tone> tones = tone_view.getTonesList();
		assertTrue("No Keys pressed", tones.size() > 0);
		
        ArrayList<Integer> midi_values = new ArrayList<Integer>();
        
        for(Tone t: tones)
        {
			ArrayList<Integer> val = t.getMidiValues();
			midi_values.add(val.get(0));			
		}
		assertTrue("Scrolling did not work", midi_values.get(0) < midi_values.get(2));
		assertTrue("Scrolling did not work", midi_values.get(3) < midi_values.get(0));

		
		
	}
	
	public void testPianoAndLockscreen()	{
		
	}
	
}