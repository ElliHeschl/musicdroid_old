package at.tugraz.ist.musicdroid.test;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.test.AndroidTestCase;
import at.tugraz.ist.musicdroid.MidiFile;
import at.tugraz.ist.musicdroid.SoundPlayer;


public class SoundPlayerTest extends AndroidTestCase {
	
	private File directory = null;
	private SoundPlayer soundplayer = null;
	
	@Override
	protected void setUp() throws Exception {
		directory = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "records" + File.separator
				+ "piano_midi_sounds");
		if (!directory.exists()) {
			directory.mkdirs();
		}
		soundplayer = new SoundPlayer(this.getContext());
		soundplayer.initSoundpool();
	}

	@Override
	protected void tearDown() throws Exception {
		File files[] = directory.listFiles();
		for(int i = 0; i < files.length; i++)
			files[i].delete();
		directory.delete();
		super.tearDown();
	}	
	
	private void createTestMidiSounds() {
		String midi_note;
		String file_name;
		String path; 

		int counter = 0;
		for (int midi_value = 36; midi_value < 96; midi_value++) {
			midi_note = getMidiNote(counter);
			file_name = midi_note + "_" + midi_value + ".mid";
			path = directory.getAbsolutePath() + File.separator + file_name;
			File file = new File(path);
			if (!file.exists()) {
				MidiFile midiFile = new MidiFile();
				midiFile.noteOn(0, midi_value, 127);
				midiFile.noteOff(64, midi_value);
				try {
					midiFile.writeToFile(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			counter++;
			if (counter == 12)
				counter = 0;
		    soundplayer.setSoundpool(path);
		}
	}
	
	private String getMidiNote(int value) {
		switch (value) {
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
	
	public void testFillSoundpool()
	{
		int id_start = soundplayer.getSoundID();
		createTestMidiSounds();
		int id_end = soundplayer.getSoundID();
		assertTrue("Pool remained empty", id_start < id_end);
	}
	
	public void testPlayNote()
	{
		createTestMidiSounds();
		soundplayer.playNote(65);
		assertTrue("not playing", soundplayer.isNotePlaying(65));
	}
	
	public void testPlayAndStopNote()
	{
		createTestMidiSounds();
		soundplayer.playNote(65);
		assertTrue("not playing", soundplayer.isNotePlaying(65));
		soundplayer.stopNote(65);
		assertFalse("not playing", soundplayer.isNotePlaying(65));
	}
	
	
	
	
}
