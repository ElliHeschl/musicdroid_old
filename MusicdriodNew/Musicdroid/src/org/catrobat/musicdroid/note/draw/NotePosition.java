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
package org.catrobat.musicdroid.note.draw;

import org.catrobat.musicdroid.note.Key;
import org.catrobat.musicdroid.note.NoteName;
import org.catrobat.musicdroid.note.Note;

/**
 * @author Bianca
 * 
 */
public class NotePosition {
	
	private NotePosition() {
	}

	public static double getLinePosition(Key key, Note tone) {
		if (key == Key.VIOLIN)
			return getToneDistanceFromToneToMiddleLineInHalfTones(key, tone);
			
		throw new UnsupportedOperationException();
	}

	private static int getToneDistanceFromToneToMiddleLineInHalfTones(Key key, Note tone) {
		NoteName currentNote = tone.getNoteName();
		NoteName middleNote = NoteName.B3;
		
		return NoteName.calculateDistance(currentNote, middleNote);
	}
}
