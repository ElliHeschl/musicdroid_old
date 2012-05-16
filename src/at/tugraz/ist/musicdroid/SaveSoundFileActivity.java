package at.tugraz.ist.musicdroid;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import at.tugraz.ist.musicdroid.common.SoundFile;

public class SaveSoundFileActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState)  {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.savesoundfile);
	}
	
	public void onSaveClick(View v)
    {
		try
		  {			  
			  String file_name = ((EditText)findViewById(R.id.filenameEdit)).getText().toString();
			  String path = ((EditText)findViewById(R.id.locationEdit)).getText().toString();
			  
			  if(!file_name.contains("."))
			  {				  
				  file_name = file_name + ".mp3";
			  }
			  
			  String src_path = SoundFile.GetInstance().getFilePath();
			  if(src_path == null)
			  {
				  Log.v("musicdroid", "onActivityResult(): No Src-File!");
				  return;	    	  
			  }
			  File src_file = new File(src_path);    	  
			  
			  
			  String dest_path = path + "/" + file_name;
			  File dest_file = new File(dest_path);
			  
			  SoundFile.GetInstance().SaveFile(src_file, dest_file);
		  }
		  catch (Exception e) {
			// TODO: handle exception
			  Log.v("musicdroid", "Exception: " + e.getMessage());
		  }
		
    	setResult(Activity.RESULT_OK);
    	finish();
    }
	
	public void onCancelClick(View v)
    {
		setResult(Activity.RESULT_CANCELED);
    	finish();
    }
	
	public void onBrowseClick(View v)
    {
		Intent myIntent = new Intent(SaveSoundFileActivity.this, FolderBrowserActivity.class);
        this.startActivityForResult(myIntent, 1001);
    }
	
	 public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		if(requestCode == 1001)  // FolderBrowser
	    {
			if(resultCode == RESULT_OK)
			{
				String location = data.getStringExtra("location");
				((EditText)findViewById(R.id.locationEdit)).setText(location);	     
			}
	    }
    }
}
