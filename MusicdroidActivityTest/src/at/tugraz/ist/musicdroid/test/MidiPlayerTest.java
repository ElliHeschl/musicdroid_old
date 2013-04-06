package at.tugraz.ist.musicdroid.test;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.test.AndroidTestCase;
import at.tugraz.ist.musicdroid.DrawTonesView;
import at.tugraz.ist.musicdroid.MidiPlayer;

public class MidiPlayerTest extends AndroidTestCase {

	MidiPlayer mp;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Context context = this.mContext;
		mp = new MidiPlayer(new DrawTonesView(context, 1, 3, 20, true), context);
	}

	public void createMidifilePathTest() {
		mp.createMidifilePath();
		File dir = new File(Environment.getExternalStorageDirectory()
				+ File.separator + "records" + File.separator + "Musicfiles");

		assertEquals(true, dir.exists());

		File file = new File(dir.getAbsolutePath() + File.separator
				+ "play.mid");

		assertEquals(false, file.exists());

		// check file exists
	}
	
	public void writeToMidiFileTest(){
		
	}

}
