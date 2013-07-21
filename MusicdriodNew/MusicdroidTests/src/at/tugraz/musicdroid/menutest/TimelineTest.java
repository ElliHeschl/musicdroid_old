/*******************************************************************************
 * Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 * 
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://www.catroid.org/catroid/licenseadditionalterm
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 * 
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package at.tugraz.musicdroid.menutest;

import com.jayway.android.robotium.solo.Solo;

import at.tugraz.musicdroid.MainActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.soundmixer.ObservableHorizontalScrollView;
import at.tugraz.musicdroid.soundmixer.SoundMixer;
import at.tugraz.musicdroid.soundmixer.timeline.Timeline;
import at.tugraz.musicdroid.soundtracks.SoundTrack;
import at.tugraz.musicdroid.soundtracks.SoundTrackView;
import at.tugraz.musicdroid.types.SoundType;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class TimelineTest extends ActivityInstrumentationTestCase2<MainActivity> {
	protected Solo solo = null;
	protected SoundMixer mixer = null;
	protected Timeline timeline = null;
	protected UITestHelper helper;
	protected ImageButton playImageButton = null;
	protected View currentPositionView =  null;
	
	public TimelineTest() {
		super(MainActivity.class);
	}
	
	@Override
	protected void setUp() {
		 solo = new Solo(getInstrumentation(), getActivity());
		 mixer = SoundMixer.getInstance();
		 helper = new UITestHelper(solo, getActivity());
		 timeline = (Timeline) getActivity().findViewById(R.id.timeline);
		 playImageButton = (ImageButton) getActivity().findViewById(R.id.btn_play);
		 currentPositionView = (View) timeline.findViewById(R.id.timeline_currentPosition);
	}
	
	
	public void testTrackLengthSettings()
	{
		int numTextViewsTopBegin = ((RelativeLayout)timeline.getChildAt(0)).getChildCount();
		int numViewsBottomBegin = ((RelativeLayout)timeline.getChildAt(1)).getChildCount();
		solo.clickOnView(getActivity().findViewById(R.id.btn_settings));
		solo.waitForText(getActivity().getString(R.string.soundmixer_context_title));
		solo.clickOnView(getActivity().findViewById(R.id.soundmixer_context_length));
		solo.sleep(1000);
		solo.drag(Helper.getInstance().getScreenWidth()/2-50, Helper.getInstance().getScreenHeight()/2, 
				  Helper.getInstance().getScreenWidth()/2-50, Helper.getInstance().getScreenHeight()/3, 1);
		solo.sleep(1000);
		solo.clickOnText(getActivity().getString(R.string.settings_button_apply));
		solo.sleep(1000);
		assertTrue("SoundMixer is not scrollable", helper.scrollToSide(timeline));
		
		int numTextViewsTopEnd = ((RelativeLayout)timeline.getChildAt(0)).getChildCount();
		int numViewsBottomEnd = ((RelativeLayout)timeline.getChildAt(1)).getChildCount();
		
		Log.i("Begin: " + numTextViewsTopBegin, "End: " + numTextViewsTopEnd);
		Log.i("Begin: " + numViewsBottomBegin, "End: " + numViewsBottomEnd);
		assertFalse(numTextViewsTopBegin == numTextViewsTopEnd);
		assertFalse(numViewsBottomBegin == numViewsBottomEnd);
		
		//add one View for each second but only one TextView each 5 seconds
		assertTrue(numViewsBottomBegin >= numTextViewsTopBegin*5); 
		assertTrue(numViewsBottomEnd >= numTextViewsTopEnd*5);
		
	}
	
	public void startEndMarkerTest()
	{
		int[] timelineLocation = {0,0};
		timeline.getLocationOnScreen(timelineLocation);
		
		int clickXPosition = timelineLocation[0]+200;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		View startMarker = timeline.findViewById(R.id.timeline_start_point);
		int margin = ((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin;
		int pixelPerSecond = SoundMixer.getInstance().getPixelPerSecond();
		
		Log.i("StartEndMarkerTest", "Margin: " + margin + " ClickX: " + clickXPosition + " PpS: " + pixelPerSecond);
		assertTrue(margin >= clickXPosition-pixelPerSecond*2 && margin <= clickXPosition+pixelPerSecond*2);
		
		//End Marker
		clickXPosition = timelineLocation[0] + 600;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_end_point);
		View endMarker = timeline.findViewById(R.id.timeline_end_point);
		int marginEnd = ((RelativeLayout.LayoutParams)endMarker.getLayoutParams()).leftMargin;
		
		Log.i("StartEndMarkerTest", "Margin: " + marginEnd + " ClickX: " + clickXPosition + " PpS: " + pixelPerSecond);
		assertTrue(marginEnd >= clickXPosition-pixelPerSecond && marginEnd <= clickXPosition+pixelPerSecond);
	}
	
	public void testStartMarkerBeforeEndMarker()
	{
		int[] timelineLocation = {0,0};
		timeline.getLocationOnScreen(timelineLocation);
		int clickXPosition = timelineLocation[0]+200;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		View startMarker = timeline.findViewById(R.id.timeline_start_point); 
		
		//place endMarker before startMarker - will fail
		clickXPosition = timelineLocation[0] + 100;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_end_point);
		
		View endMarker = timeline.findViewById(R.id.timeline_end_point);
		assertTrue(endMarker.getVisibility() == View.GONE);
		
		//place endMarker behind startMarker
		clickXPosition = timelineLocation[0] + 400;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_end_point);
		
		//place startMarker behind endMarker - will fail
		int oldMarginStartMarker = ((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin;
		clickXPosition = timelineLocation[0]+600;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		int newMarginStartMarker = ((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin;
		assertTrue(oldMarginStartMarker == newMarginStartMarker);
		
	}
	
	public void testMoveMarker()
	{
		int[] timelineLocation = {0,0};
		timeline.getLocationOnScreen(timelineLocation);
		int clickXPosition = timelineLocation[0]+200;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_start_point);
		View startMarker = timeline.findViewById(R.id.timeline_start_point);
		
		
		clickXPosition = timelineLocation[0] + 400;
		helper.addTimelineMarker(clickXPosition, timelineLocation[1], R.string.timeline_menu_entry_end_point);
		View endMarker = timeline.findViewById(R.id.timeline_end_point);
		
		int[] startMarkerLocation = {0,0};
		startMarker.getLocationOnScreen(startMarkerLocation);
		
		int startMargin = ((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin;
		int endMargin = ((RelativeLayout.LayoutParams)endMarker.getLayoutParams()).leftMargin;
		
		int drag = endMargin - startMargin;
		
		solo.drag(startMarkerLocation[0], startMarkerLocation[0]+ drag + 10, 
		          startMarkerLocation[1], startMarkerLocation[1], 20);
		
		solo.sleep(1000);
		
		assertTrue(((RelativeLayout.LayoutParams)startMarker.getLayoutParams()).leftMargin <
					((RelativeLayout.LayoutParams)endMarker.getLayoutParams()).leftMargin);
	}
	
	public void testTimelineProgressBar()
	{
		LayoutParams lp = (LayoutParams) currentPositionView.getLayoutParams();
		assertTrue(lp.width == 0);
		helper.addTrack(SoundType.DRUMS);
		solo.clickOnView(playImageButton);
		solo.sleep(5000);
		
		lp = (LayoutParams) currentPositionView.getLayoutParams();
		assertTrue(lp.width >= SoundMixer.getInstance().getPixelPerSecond()*4);
		 
		//assert progressbar remains unchanged after pressing stop button
		solo.clickOnView(playImageButton);
		lp = (LayoutParams) currentPositionView.getLayoutParams();
		assertTrue(lp.width >= SoundMixer.getInstance().getPixelPerSecond()*5);
	}
	
	public void testTimelineProgessBarResetOnRewind()
	{
		testTimelineProgressBar();
		ImageButton rewindImageButton = (ImageButton) getActivity().findViewById(R.id.btn_rewind);
		solo.clickOnView(rewindImageButton);
		
		LayoutParams lp = (LayoutParams) currentPositionView.getLayoutParams();
		assertTrue(lp.width == 0);
	}
	
	public void testTimelineProgressBarUnchangedOnPause()
	{
		testTimelineProgressBar();
		
		LayoutParams lp = (LayoutParams) currentPositionView.getLayoutParams();
		int startWidth = lp.width;
		
		solo.clickOnView(playImageButton);
		solo.sleep(1000);
		lp = (LayoutParams) currentPositionView.getLayoutParams();
		assertTrue(lp.width >= startWidth + SoundMixer.getInstance().getPixelPerSecond());
		solo.clickOnView(playImageButton);
	}

}



