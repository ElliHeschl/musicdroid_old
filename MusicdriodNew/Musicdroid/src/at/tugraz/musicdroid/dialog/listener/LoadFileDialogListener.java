package at.tugraz.musicdroid.dialog.listener;

import android.app.Activity;
import android.util.Log;
import at.tugraz.musicdroid.DrumsActivity;

public class LoadFileDialogListener extends DialogListener {
	

	public LoadFileDialogListener(Activity a) {
		super(a);
	}

	public void onStringChanged(String str)
	{
		if(activity.getClass() == DrumsActivity.class)
		{
			Log.i("LoadFileDialogListener", "Load File " + str);
			((DrumsActivity)activity).loadPresetByName(str);
		}
		else
		{
			Log.i("LoadFileDialogListener", "Activity not class DrumsActivity");
		}
	}
}
