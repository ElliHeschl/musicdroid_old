package at.tugraz.ist.musicdroid.tests;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListAdapter;
import android.widget.ListView;
import at.tugraz.ist.musicdroid.R;
import at.tugraz.ist.musicdroid.SaveSoundFileActivity;

import com.jayway.android.robotium.solo.Solo;

public class SaveSoundFileActivityTest extends ActivityInstrumentationTestCase2<SaveSoundFileActivity> { //aktivity gibt an, wo test startet
    private
     Solo solo; 
	
	public SaveSoundFileActivityTest() {
		super("at.tugraz.ist.musicdroid", SaveSoundFileActivity.class);
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
		super.tearDown();	
	}

	public void testButtons() throws InterruptedException{		
		assertTrue("Save Button not found", solo.searchButton(solo.getString(R.string.save), true));
		assertTrue("Cancel Button not found", solo.searchButton(solo.getString(R.string.cancel), true));
		assertTrue("Browse Button not found", solo.searchButton(solo.getString(R.string.browse), true));
	}
	
	public void testNewFolder() throws InterruptedException{		
		solo.clickOnButton(solo.getString(R.string.browse));
		
		solo.clickOnMenuItem("sdcard");
		Activity A = solo.getCurrentActivity(); 
		ListView lv = (ListView)A.findViewById(android.R.id.list);
		ListAdapter la = lv.getAdapter();
		int cnt_old = la.getCount();
		
		
		solo.clickOnButton(solo.getString(R.string.new_folder));
		
		la = lv.getAdapter();
		int cnt_new = la.getCount();
		boolean result = ((cnt_old+1) == cnt_new);
		assertTrue("Folder could not be created!", result);		
	}
	
	public void testSelectSDCard() throws InterruptedException{		
		solo.clickOnButton(solo.getString(R.string.browse));
		
		solo.clickOnMenuItem("sdcard");
		solo.clickOnButton(solo.getString(R.string.ok));
		assertTrue("Wrong path", solo.searchText("/sdcard",true));
		
	}
}
