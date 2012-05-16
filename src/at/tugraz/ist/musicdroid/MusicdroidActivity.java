package at.tugraz.ist.musicdroid;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import at.tugraz.ist.musicdroid.common.SoundFile;


public class MusicdroidActivity extends Activity {
    /** Called when the activity is first created. */

	SoundFile sound_file;
	private final int REQUEST_SELECT_MUSIC = 0;
	TextView my_list_view;
	String filename_;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//sound_file = new SoundFile();
		// Branch: open_soundfile!!!
		my_list_view = (TextView) findViewById(R.id.textView1);
	}
    
    public void handleLoadFileButton(View v){
			Log.v("musicdroid", "button geklickt!!");
			LoadFile();
			
			
	}
	
	
	public void LoadFile() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("audio/*");
		startActivityForResult(Intent.createChooser(intent,
				getString(R.string.load_sound_file_chooser_text)),
				REQUEST_SELECT_MUSIC);

	}
    
    public void openFolderBrowser(View v)
    {
    	Intent myIntent = new Intent(MusicdroidActivity.this, FolderBrowserActivity.class);
        this.startActivityForResult(myIntent, 1001);
        
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		if(requestCode == 1001)  // FolderBrowser
	    {
	      if(resultCode == RESULT_OK)
	      {	    	  
	    	  // data.getStringExtra("location") returns the selected path
	    	  // there's no file to save yet, so we create dialog and present the selected path!
	    	  new AlertDialog.Builder(this)
				.setIcon(R.drawable.musicdroid_launcher)
				.setTitle(data.getStringExtra("location"))
				.setPositiveButton("OK", 
						new DialogInterface.OnClickListener() {
							
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						}).show();
	      }
	    }
	    else
	    {
    		super.onActivityResult(requestCode, resultCode, data);		
			SoundFile.GetInstance().LoadFile(requestCode, resultCode, data);
		}
    
    }
}
