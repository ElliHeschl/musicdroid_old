package at.tugraz.ist.musicdroid;


import java.io.File;

import android.app.Activity;
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
			  try
			  {			  
				  String project_name = "project";
				  String extension = ".mp3";
				  
				  String src_path = SoundFile.GetInstance().getFilePath();
				  if(src_path == null)
				  {
					  Log.v("musicdroid", "onActivityResult(): No Src-File!");
					  return;	    	  
				  }
				  File src_file = new File(src_path);    	  
				  
				  
				  String dest_path = data.getStringExtra("location") + "/" + project_name + extension;
				  File dest_file = new File(dest_path);
				  
				  SoundFile.GetInstance().SaveFile(src_file, dest_file);
			  }
			  catch (Exception e) {
				// TODO: handle exception
				  Log.v("musicdroid", "Exception: " + e.getMessage());
			  }
			}
	      
	    }
	    else
	    {
    		super.onActivityResult(requestCode, resultCode, data);		
			SoundFile.GetInstance().LoadFile(requestCode, resultCode, data);
		}
    
    }
}
