package at.tugraz.musicdroid.helper;

import android.util.Log;

public class InputValidator {
	private static InputValidator instance = null;
	final String[] RESERVED_CHARS = {"|", "\\", "?", "*", "<", "\"", ":", ">", "[", "]", "'"};

	
	public static InputValidator getInstance() {
        if (instance == null) {
            instance = new InputValidator();
        }
        return instance;
    }
	
	public boolean isValidFilename(String filename)
	{
		for(String c : RESERVED_CHARS){
			if(filename.contains(c))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean isValidFilenameWithoutPath(String filename)
	{
		Log.i("InputValidator", "Filename = " + filename);
		return isValidFilename(filename) && !filename.contains("/");
	}
	
	
}
