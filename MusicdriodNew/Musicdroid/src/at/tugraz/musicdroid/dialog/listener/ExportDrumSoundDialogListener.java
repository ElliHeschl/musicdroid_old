package at.tugraz.musicdroid.dialog.listener;

import android.app.Activity;
import android.util.Log;
import at.tugraz.musicdroid.DrumsActivity;

public class ExportDrumSoundDialogListener extends DialogListener {
	

	public ExportDrumSoundDialogListener(Activity a) {
		super(a);
	}

	public void onStringChanged(String str)
	{

	}
	
	public void onIntChanged(int integer)
	{
		
		//((DrumsActivity)activity).returnToMainActivity(integer);
	}
}
