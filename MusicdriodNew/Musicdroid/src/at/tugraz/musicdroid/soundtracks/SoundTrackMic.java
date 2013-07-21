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
package at.tugraz.musicdroid.soundtracks;

import java.util.Observable;

import android.util.Log;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.SoundManager;
import at.tugraz.musicdroid.helper.Helper;
import at.tugraz.musicdroid.types.SoundType;

public class SoundTrackMic extends SoundTrack {

	public SoundTrackMic() {
		type = SoundType.MIC;
		name = "SoundfileMic";
		soundfileRawId = R.raw.test_wav;
		duration =  SoundManager.getInstance().getSoundfileDuration(soundfileRawId);
		soundpoolId = SoundManager.getInstance().loadSound(soundfileRawId);
		Log.i("SoundTrackMIC", "SoundpoolID = " + soundpoolId);
	}

	public SoundTrackMic(String path) {
		type = SoundType.MIC;
		name = Helper.getInstance().getFilenameFromPath(path);
		soundpoolId = SoundManager.getInstance().addSoundByPath(path);
		duration = SoundManager.getInstance().getSoundfileDurationByPath(path);		
	}
	
	public SoundTrackMic(SoundTrackMic stm)
	{
		super(stm);
		name = stm.name;
		soundpoolId = stm.soundpoolId;
		duration = stm.duration;
		soundpoolId = SoundManager.addSoundByPath(path);
		duration = SoundManager.getSoundfileDurationByPath(path);
	}

	 
	@Override
	public void update(Observable observable, Object data) {
		int cur_time = (Integer)data;
		Log.i("Incoming ObjectMic: ", "" + cur_time);
		if(cur_time == startPoint)
		{
			Log.i("SoundTrackMic ", "PlaySound");
			SoundManager.getInstance().playSound(soundpoolId, 1, volume);
		}
	}

}
