package at.tugraz.musicdroid.dialog.listener;

import android.app.Activity;

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
