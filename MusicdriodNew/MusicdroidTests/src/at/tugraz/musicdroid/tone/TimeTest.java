package at.tugraz.musicdroid.tone;

import junit.framework.TestCase;

public class TimeTest extends TestCase {

	public void testTime1() {
		Time time = new Time();
		
		assertEquals(4, time.getBeatsPerTact());
		assertEquals(NoteValue.QUARTER, time.getNoteLength());
	}
	
	public void testTime2() {
		Time time = new Time(19, NoteValue.EIGHT);
		
		assertEquals(19, time.getBeatsPerTact());
		assertEquals(NoteValue.EIGHT, time.getNoteLength());
	}
	
	public void testEquals1() {
		Time time1 = new Time(19, NoteValue.EIGHT);
		Time time2 = new Time(19, NoteValue.EIGHT);
		
		assertTrue(time1.equals(time2));
	}
	
	public void testEquals2() {
		Time time1 = new Time(19, NoteValue.EIGHT);
		Time time2 = new Time(20, NoteValue.EIGHT);
		
		assertFalse(time1.equals(time2));
	}
	
	public void testEquals3() {
		Time time1 = new Time(19, NoteValue.EIGHT);
		Time time2 = new Time(20, NoteValue.SIXTEENTH);
		
		assertFalse(time1.equals(time2));
	}
	
	public void testEquals4() {
		Time time = new Time();
		
		assertFalse(time.equals(null));
	}
	
	public void testEquals5() {
		Time time = new Time();
		
		assertFalse(time.equals(""));
	}
}