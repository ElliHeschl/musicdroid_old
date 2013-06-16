package at.tugraz.musicdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import at.tugraz.musicdroid.animation.HighlightAnimation;
import at.tugraz.musicdroid.dialog.MetronomQuickSettingsDialog;
import at.tugraz.musicdroid.dialog.OpenFileDialog;
import at.tugraz.musicdroid.dialog.SavePresetDialog;
import at.tugraz.musicdroid.dialog.listener.LoadFileDialogListener;
import at.tugraz.musicdroid.drums.DrumLoopEventHandler;
import at.tugraz.musicdroid.drums.DrumPreset;
import at.tugraz.musicdroid.drums.DrumPresetHandler;
import at.tugraz.musicdroid.drums.DrumSoundRow;
import at.tugraz.musicdroid.drums.DrumsLayoutManager;
import at.tugraz.musicdroid.drums.DrumsMenuCallback;
import at.tugraz.musicdroid.drums.StatusbarDrums;
import at.tugraz.musicdroid.recorder.AudioHandler;
import at.tugraz.musicdroid.soundmixer.SoundMixer;

public class DrumsActivity extends FragmentActivity {
	private DrumsLayoutManager drumsLayout = null;
	private DrumLoopEventHandler drumLoopEventHandler = null;
	private DrumPresetHandler drumPresetHandler = null;
	private SavePresetDialog savePresetDialog = null;
	private OpenFileDialog openFileDialog = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drums);
        
        drumLoopEventHandler = new DrumLoopEventHandler();
        drumsLayout = new DrumsLayoutManager(this);
        
        initTopStatusBar();
        StatusbarDrums.getInstance().initStatusbar(this);
        
        drumPresetHandler = new DrumPresetHandler(this);		
        savePresetDialog = new SavePresetDialog();
		openFileDialog = new OpenFileDialog(this, new LoadFileDialogListener(this), DrumPresetHandler.path, ".xml");
	}

	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.drums_menu, menu);
		return true;
	}
    
    @Override
    protected void onResume()
    {
    	super.onResume();
    }

    @Override
    protected void onPause()
    {
    	super.onPause();
    }
    
	@Override
    public void onBackPressed() {
		if(drumsLayout.hasUnsavedChanges())
		{
		    showSecurityQuestionBeforeGoingBack();
		}
		else
		{
			drumLoopEventHandler.stop();
			drumsLayout.resetLayout();
		    finish();
		}
	} 
    
	private void showSecurityQuestionBeforeGoingBack() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.drums_back_pressed_security_question);
		builder.setCancelable(true);
		builder.setNegativeButton(R.string.yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						drumLoopEventHandler.stop();
						drumsLayout.resetLayout();
						finish();
					}
				});
		builder.setPositiveButton(R.string.no,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
						HighlightAnimation.getInstance().highlightViewAnimation(findViewById(R.id.drums_context_save_preset ));
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
    
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Log.i("DrumsActivity", "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.btn_settings:
			DrumsMenuCallback callbackDrumsMenu = new DrumsMenuCallback(this);
			startActionMode(callbackDrumsMenu);
			return true;
        case R.id.drums_context_save_preset:
        	savePresetDialog.show(getFragmentManager(), null);
        	//parent.saveCurrentPreset("example");
        	break;
        case R.id.drums_context_load_preset:
        	openFileDialog.showDialog();
        	//parent.loadPresetByName("example");
        	break;
        case R.id.drums_context_clear_preset:
        	drumsLayout.resetLayout();
        	drumLoopEventHandler.deleteObservers();
		}
		return false;
	}	
	
	
	public void saveCurrentPreset(String name)
	{
		if(drumPresetHandler.writeDrumLoopToPreset(name, drumsLayout.getDrumSoundRowsArray()))
			drumsLayout.setUnsavedChanges(false);
	}
	
	public void loadPresetByName(String name)
	{
		DrumPreset preset = null;
		if((preset = drumPresetHandler.readPresentByName(name)) != null)
		  drumsLayout.loadPresetToDrumLayout(preset);
	}
	
    private void initTopStatusBar()
    {
		getActionBar().setCustomView(R.layout.status_bar_drums);
		getActionBar().setDisplayShowHomeEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setIcon(R.drawable.ic_launcher); 		
    }
    
    public void returnToMainActivtiy()
    {
    	 Intent returnIntent = new Intent();
    	 returnIntent.putExtra("drums_filename",AudioHandler.getInstance().getFilenameFullPath());
    	 setResult(RESULT_OK,returnIntent);
    	 finish();
    }

    public void addObserverToEventHandler(DrumSoundRow dsr)
    {
    	drumLoopEventHandler.addObserver(dsr);
    }
    
    public void startPlayLoop()
    {
    	drumLoopEventHandler.play();
    }
    
    public void stopPlayLoop()
    {
    	drumLoopEventHandler.stop();
    }
    
    public DrumLoopEventHandler getDrumLoopEventHandler()
    {
    	return drumLoopEventHandler;
    }
    
    public DrumsLayoutManager getDrumsLayoutManager()
    {
    	return drumsLayout;
    }

    
}