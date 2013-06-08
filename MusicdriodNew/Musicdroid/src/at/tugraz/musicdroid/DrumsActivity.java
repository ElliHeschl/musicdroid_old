package at.tugraz.musicdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import at.tugraz.musicdroid.drums.DrumsLayoutManager;
import at.tugraz.musicdroid.recorder.AudioHandler;

public class DrumsActivity extends FragmentActivity {
	private DrumsLayoutManager drumsLayout = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drums);
        
        
        drumsLayout = new DrumsLayoutManager(this);
        
	}

	
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.recorder_menu, menu);
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

		Log.i("RecorderActivity", "onOptionsItemSelected");
		switch (item.getItemId()) {
		case R.id.btn_settings:
			return true;
		}
		return false;
	}	
    
    public void returnToMainActivtiy()
    {
    	 Intent returnIntent = new Intent();
    	 returnIntent.putExtra("drums_filename",AudioHandler.getInstance().getFilenameFullPath());
    	 setResult(RESULT_OK,returnIntent);
    	 finish();
    }


    
}
