package at.tugraz.ist.musicdroid;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import at.tugraz.ist.musicdroid.common.SoundFile;


public class MusicdroidActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */

    
    private Button OpenRecorderButton;
    private Button OpenPlayerButton;
    private Button OpenProjectButton;
    private Button NewProjectButton;

	SoundFile sound_file;
	private final int REQUEST_SELECT_MUSIC = 0;
	TextView my_list_view;
	String filename_;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        OpenRecorderButton = (Button)findViewById(R.id.soundRecorderButton);
        OpenRecorderButton.setOnClickListener(this);
        OpenPlayerButton = (Button)findViewById(R.id.soundPlayerButton);
        OpenPlayerButton.setOnClickListener(this);
        OpenProjectButton = (Button)findViewById(R.id.openProjectButton);
        OpenProjectButton.setOnClickListener(this);
        NewProjectButton = (Button)findViewById(R.id.newProjectButton);
        NewProjectButton.setOnClickListener(this);

		my_list_view = (TextView) findViewById(R.id.textView1);
	}
	
	
	public void LoadFile() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("audio/*");
		startActivityForResult(Intent.createChooser(intent,
				getString(R.string.load_sound_file_chooser_text)),
				REQUEST_SELECT_MUSIC);

	}
    
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {		
		super.onActivityResult(requestCode, resultCode, data);		
		SoundFile.GetInstance().LoadFile(requestCode, resultCode, data);
    
    }

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == NewProjectButton)
		{
		// new project
			Intent myIntent = new Intent(MusicdroidActivity.this, SaveSoundFileActivity.class);
	        this.startActivity(myIntent); 
		}
		if (arg0 == OpenProjectButton)
		{
		// open project
		}
		if (arg0 == OpenPlayerButton)
		{
		// open player
		}
		if (arg0 == OpenRecorderButton)
		{
		// open Recorder
		}
	}
}

