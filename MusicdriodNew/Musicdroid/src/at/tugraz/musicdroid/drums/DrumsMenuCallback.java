package at.tugraz.musicdroid.drums;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import at.tugraz.musicdroid.DrumsActivity;
import at.tugraz.musicdroid.R;
import at.tugraz.musicdroid.RecorderActivity;
import at.tugraz.musicdroid.dialog.MetronomQuickSettingsDialog;
import at.tugraz.musicdroid.dialog.SavePresetDialog;
import at.tugraz.musicdroid.preferences.PreferenceManager;

public class DrumsMenuCallback implements ActionMode.Callback {
		DrumsActivity parent = null;
		private MetronomQuickSettingsDialog metronomDialog = null;
		private SavePresetDialog savePresetDialog = null;
	
		public DrumsMenuCallback(DrumsActivity p)
		{
			parent = p;
			metronomDialog = new MetronomQuickSettingsDialog();
			savePresetDialog = new SavePresetDialog();
		}
	
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            parent.getMenuInflater().inflate(R.menu.drums_callback_menu, menu);
            mode.setTitle(R.string.drums_context_title);
           
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
            case R.id.recorder_context_bpm:
            	metronomDialog.show(parent.getFragmentManager(), null);
                mode.finish();
                break;
            case R.id.drums_context_save_preset:
            	Log.i("DrumsMenuCallback", "SavePreset");
            	savePresetDialog.show(parent.getFragmentManager(), null);
            	//parent.saveCurrentPreset("example");
            	mode.finish();
            	break;
            case R.id.drums_context_load_preset:
            	Log.i("DrumsMenuCallback", "LoadPreset");
            	parent.loadPresetByName("example");
            	mode.finish();
            	break;
            }
            return false;
        }

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			// TODO Auto-generated method stub
			
		}
}
