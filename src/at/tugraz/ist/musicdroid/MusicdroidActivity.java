package at.tugraz.ist.musicdroid;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MusicdroidActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
    }
    
    public void openFolderBrowser(View v)
    {
    	Intent myIntent = new Intent(MusicdroidActivity.this, FolderBrowserActivity.class);
        this.startActivityForResult(myIntent, 1001);
        
    }
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
		if(requestCode == 1001)
	    {
	      if(resultCode == RESULT_OK)
	      {
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
    	
    
    }
}
