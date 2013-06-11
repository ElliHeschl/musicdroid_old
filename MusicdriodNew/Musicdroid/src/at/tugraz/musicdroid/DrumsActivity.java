package at.tugraz.musicdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import at.tugraz.musicdroid.drums.DrumLoopEventHandler;
import at.tugraz.musicdroid.drums.DrumPreset;
import at.tugraz.musicdroid.drums.DrumPresetHandler;
import at.tugraz.musicdroid.drums.DrumSoundRow;
import at.tugraz.musicdroid.drums.DrumsLayoutManager;
import at.tugraz.musicdroid.drums.DrumsMenuCallback;
import at.tugraz.musicdroid.drums.StatusbarDrums;
import at.tugraz.musicdroid.recorder.AudioHandler;
import at.tugraz.musicdroid.recorder.RecorderMenuCallback;
import at.tugraz.musicdroid.soundmixer.Statusbar;

public class DrumsActivity extends FragmentActivity {
	private DrumsLayoutManager drumsLayout = null;
	private DrumLoopEventHandler drumLoopEventHandler = null;
	private DrumPresetHandler drumPresetHandler = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drums);
        
        drumLoopEventHandler = new DrumLoopEventHandler();
        drumsLayout = new DrumsLayoutManager(this);
        
        initTopStatusBar();
        StatusbarDrums.getInstance().initStatusbar(this);
        
        drumPresetHandler = new DrumPresetHandler(this);
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
	public boolean onOptionsItemSelected(MenuItem item) {

		Log.i("DrumsActivity", "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.btn_settings:
			DrumsMenuCallback callbackDrumsMenu = new DrumsMenuCallback(this);
			startActionMode(callbackDrumsMenu);
			return true;
		}
		return false;
	}	
	
	
	public void saveCurrentPreset(String name)
	{
		drumPresetHandler.writeDrumLoopToPreset(name, drumsLayout.getDrumSoundRowsArray());
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
    

    
}
