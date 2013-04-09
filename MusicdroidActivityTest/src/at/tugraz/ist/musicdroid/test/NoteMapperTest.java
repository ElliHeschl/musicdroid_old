package at.tugraz.ist.musicdroid.test;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;
import at.tugraz.ist.musicdroid.MidiFile;
import at.tugraz.ist.musicdroid.NoteMapper;
import at.tugraz.ist.musicdroid.SoundPlayer;
import at.tugraz.ist.musicdroid.common.Projekt;


public class NoteMapperTest extends AndroidTestCase {
	
	private NoteMapper nm = null;
	
	@Override
	protected void setUp() throws Exception {
		nm = new NoteMapper();
		int x = 1000;
		nm.initializeWhiteKeyMap(x / (float) 35);
		nm.initializeBlackKeyMap(x / (float) 60);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}	
	
	
	public void testgetWhiteKeyFromPosition()
	{
		assertTrue("Invalid Lookup", nm.getWhiteKeyFromPosition(275) == 52);
		assertTrue("Invalid Lookup", nm.getWhiteKeyFromPosition(400) == 60);
		assertTrue("Invalid Lookup", nm.getWhiteKeyFromPosition(600) == 72);
		assertTrue("Invalid Lookup", nm.getWhiteKeyFromPosition(850) == 86);
	}
	
	public void testgetBlackKeyFromPosition()
	{
		assertTrue("Invalid Lookup", nm.getBlackKeyFromPosition(308) == 54);
		assertTrue("Invalid Lookup", nm.getBlackKeyFromPosition(580) == 70);
		assertTrue("Invalid Lookup", nm.getBlackKeyFromPosition(908) == 90);
	}
	
	public void testgetBlackKeyInvalidPosition()
	{
		assertTrue("Valid Lookup", nm.getBlackKeyFromPosition(598) == -1);
		assertTrue("Valid Lookup", nm.getBlackKeyFromPosition(752) == -1);
		assertTrue("Valid Lookup", nm.getBlackKeyFromPosition(325) == -1);
	}
	
}
