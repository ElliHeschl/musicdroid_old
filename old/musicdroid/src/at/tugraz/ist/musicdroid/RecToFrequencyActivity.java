package at.tugraz.ist.musicdroid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.puredata.android.service.PdService;
import org.puredata.android.utils.PdUiDispatcher;
import org.puredata.core.PdBase;
import org.puredata.core.PdListener;
import org.puredata.core.utils.IoUtils;
import org.puredata.core.utils.PdDispatcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import at.tugraz.ist.musicdroid.common.Constants;
import at.tugraz.ist.musicdroid.common.DataManagement;
import at.tugraz.ist.musicdroid.common.Projekt;


public class RecToFrequencyActivity extends Activity implements OnClickListener {

	private static final int PRERECORD = 0;
	private static final int RECORD = 1;
	private static final int POSTRECORD = 2;
	private int state = 0;
	private PdService pdService = null;
	private final static String Appname = "rec_to_frequency";
	private String path;
	private File dir;
	private Button StopRecordButton;
    private Button StartRecordButton;
    private Button SaveFileButton;
    private Button NextNoteButton;
    private Integer pitch;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private DrawTonesView toneView;
    ArrayList<Integer> pitches;
    private MidiFile mf = new MidiFile();
	private final ServiceConnection pdConnection = new ServiceConnection() {
    	//@Override
    	public void onServiceConnected(ComponentName name, IBinder service) {
    		Log.i("onServiceConnected", "ServiceConnection");
    		pdService = ((PdService.PdBinder)service).getService();
    		try {
    			
    			initPd();
    			
    			loadPatch();
    		} catch (IOException e) {
    			Log.e(Appname, e.toString());
    			finish();
    		} 
    	}
        //@Override
        public void onServiceDisconnected(ComponentName name) {
            } 
        };
         
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            Resources r = getResources();
    		int radius = 20;// r.getInteger(R.integer.radius);
    		int topline = r.getInteger(R.integer.topmarginlines);
    		
    		setContentView(R.layout.record_to_frequency);
    		toneView = new DrawTonesView(this, R.drawable.violine, radius , topline,false);	
    		toneView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
    		//setContentView(toneView);
    		LinearLayout layout =  (LinearLayout)findViewById(R.id.baseLayout);
            toneView.setVisibility(View.INVISIBLE);
            layout.addView(toneView);
            
            Log.i("test", "test");
            pitches = new ArrayList<Integer>();
            StopRecordButton = (Button)findViewById(R.id.stopRecordButton);
            StopRecordButton.setOnClickListener(this);
            StartRecordButton = (Button)findViewById(R.id.startRecordButton);
            StartRecordButton.setOnClickListener(this);
            SaveFileButton = (Button)findViewById(R.id.saveFileButton);
            SaveFileButton.setOnClickListener(this);
            NextNoteButton = (Button)findViewById(R.id.nextNoteButton);
            NextNoteButton.setOnClickListener(this);
			StartRecordButton.setVisibility(View.VISIBLE);
			StopRecordButton.setVisibility(View.GONE);
			NextNoteButton.setVisibility(View.GONE);
			SaveFileButton.setVisibility(View.GONE);
            bindService(new Intent(this, PdService.class),pdConnection,BIND_AUTO_CREATE);
            Log.i("bindService", "bindService");
    		builder = new AlertDialog.Builder(this);
    		builder.setMessage(R.string.save_dialog)
    				.setCancelable(false)
    				.setPositiveButton("Yes",
    						new DialogInterface.OnClickListener() {
    							public void onClick(DialogInterface dialog, int id) {
    								writeMidiFile(pitches);
    							}
    						})
    				.setNegativeButton("No", new DialogInterface.OnClickListener() {
    					public void onClick(DialogInterface dialog, int id) {
    						
    					}
    				});

    		alert = builder.create();
        }
        
        /* We'll use this to catch print statements from Pd
        when the user has a [print] object */
     private final PdDispatcher myDispatcher = new PdUiDispatcher() {
       @Override
       public void print(String s) {
         Log.i("Pd print", s);
             
       }
     };
     /* We'll use this to listen out for messages from Pd.
     Later we'll hook this up to a named receiver. */
  private final PdListener myListener = new PdListener() {	
    public void receiveMessage(String source, String symbol, Object... args) {
      Log.i("receiveMessage symbol:", symbol);
      for (Object arg: args) {
        Log.i("receiveMessage atom:", arg.toString());
      }
    }

    /* What to do when we receive a list from Pd. In this example
       we're collecting the list from Pd and outputting each atom */
    public void receiveList(String source, Object... args) {
      for (Object arg: args) {
        Log.i("receiveList atom:", arg.toString());
      }
    }

    /* When we receive a symbol from Pd */
    public void receiveSymbol(String source, String symbol) {
      Log.i("receiveSymbol", symbol);

    }
    /* When we receive a float from Pd */
    public void receiveFloat(String source, float x) {
    	if(state == RECORD)
    	{
    		Log.i("receiveFloat", ((Float)x).toString());
    		pitch = Math.round(x); 
    		Log.i("Pitch", ((Integer)pitch).toString());
    		//toneView.deleteElement(0);
    		toneView.clearList();
    		toneView.addElement(pitch);
    		
    		//toneView.refreshDrawableState();
    		//toneView.invalidate();
    		//values.add( ((Float)x).toString()); 
    	}
    }
    /* When we receive a bang from Pd */
    public void receiveBang(String source) {
      Log.i("receiveBang", "bang!");
     
    }
  };
        
        private void loadPatch() throws IOException {
        	Log.e("loadPatch", "loadPatch");
    		dir = getFilesDir();
    		IoUtils.extractZipResource(getResources().openRawResource(R.raw.fiddle),
    				dir, true);
    		File patchFile = new File(dir, "fiddle.pd");
    		path = patchFile.getAbsolutePath();		
    		PdBase.openPatch(patchFile.getAbsolutePath());	

        }
        
        private void initPd() throws IOException {
    		String name = getResources().getString(R.string.app_name);
    		Log.i("initPd", "initPd");
    		pdService.initAudio(-1, 1, -1, -1);
    		Log.i("initAudio", "initAudio");
    		pdService.startAudio(new Intent(this, RecToFrequencyActivity.class), 
    				             R.drawable.musicdroid_launcher, name, "Return to " 
    		                                                         + name + "."); 
    		Log.i("startAudio", "startAudio");		
    		/* here is where we bind the print statement catcher defined below */
      	  PdBase.setReceiver(myDispatcher);
      	  /* here we are adding the listener for various messages
      	     from Pd sent to "GUI", i.e., anything that goes into the object
      	     [s GUI] will send to the listener defined below */
      	  myDispatcher.addListener("micro", myListener);
    	}
        @Override
    	public void onDestroy() {
    		super.onDestroy();
    		unbindService(pdConnection);
    	}
        
        public void writeMidiFile(ArrayList<Integer> values) {   
    	    
        	  
        	
        	if(values.size() <= 0) return;
        	
    	    	mf.progChange(76);  //select instrument
    	    	for(int i=0;i< values.size();i++)
    		    {
    	    		mf.noteOnOffNow(MidiFile.QUAVER, values.get(i), 127);
    		    }
    	    	
    	    	AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
    			alert2.setTitle("Please enter a filename.");
    			final EditText input = new EditText(this);
    			alert2.setView(input);

    			alert2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    				
    				String value = "";

    				public void onClick(DialogInterface dialog, int which) {
    					DataManagement management = new DataManagement();
    					value = input.getText().toString();

    					
    	    	
    	    	
    	    	if (value != "") {
    	    		try
    	        	{
    	    	File directory = new File(Constants.MAIN_DIRECTORY+Constants.RECORDS_SUB_DIRECTORY);
    	    	management.checkDirectory(directory.getAbsolutePath());
    	   
    	    	//File outputFile = new File(directory, "/miditest.mid");
    	    	mf.writeToFile(directory + value);
    	    	File f2 = new File(directory + value);
    	     	Projekt.getInstance().addRecord(
						f2.getAbsolutePath());
    		    if(f2.exists())
    		    	Log.i("file exists", "file exists");
    					}
    					
    	
    	
    	catch (Exception e) {
			Log.e("Midi", e.getMessage());
		} 
    	    	}}
        });
    			alert2.show(); 	
        	
        	
    				

    			
        }
        
        public void playfile() {
        	File f = new File(Environment.getExternalStorageDirectory()+"/records/miditest.mid" );
    	    
    	    if(!f.exists()) return;
    	    
        	Uri myUri = Uri.fromFile(f);
        	MediaPlayer mediaPlayer = new MediaPlayer();
        	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        	try {
        		mediaPlayer.setDataSource(getApplicationContext(), myUri);
        	} catch (IllegalArgumentException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	} catch (SecurityException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	} catch (IllegalStateException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	try {
        		mediaPlayer.prepare();
        	} catch (IllegalStateException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	} catch (IOException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	mediaPlayer.start();	
        }
        
		//@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (arg0 == StartRecordButton)
			{
				if(state == PRERECORD)
				{
					// start record
					state = RECORD;
					StartRecordButton.setVisibility(View.GONE);
					StopRecordButton.setVisibility(View.VISIBLE);
					NextNoteButton.setVisibility(View.VISIBLE);
					SaveFileButton.setVisibility(View.GONE);
					toneView.setVisibility(View.VISIBLE);
					
				}
				else if(state == POSTRECORD)
				{
					alert.show();

					state = RECORD;
					StartRecordButton.setVisibility(View.GONE);
					StopRecordButton.setVisibility(View.VISIBLE);
					NextNoteButton.setVisibility(View.VISIBLE);
					SaveFileButton.setVisibility(View.GONE);
					toneView.setVisibility(View.VISIBLE);
					toneView.clearList();

				}
				else
				{
					Log.e("StartRecord-Error", "StartRecordButton clicked in wrong state");
				}
			}
			if (arg0 == StopRecordButton)
			{
				if(state == RECORD)
				{
					
					// Stop record
					toneView.clearList();
					state = POSTRECORD;
					StartRecordButton.setVisibility(View.VISIBLE);
					StopRecordButton.setVisibility(View.GONE);
					NextNoteButton.setVisibility(View.GONE);
					SaveFileButton.setVisibility(View.VISIBLE);
					toneView.setVisibility(View.INVISIBLE);
				}
				else
				{
					Log.e("StopRecord-Error", "StopRecordButton clicked in wrong state");
				}
			}
			if (arg0 == SaveFileButton)
			{
				if(state == POSTRECORD)
				{
					// save file
					
					writeMidiFile(pitches);
					Log.i("writeMidiFile", "writeMidiFile");
					state = PRERECORD;
					StartRecordButton.setVisibility(View.VISIBLE);
					StopRecordButton.setVisibility(View.GONE);
					NextNoteButton.setVisibility(View.GONE);
					SaveFileButton.setVisibility(View.GONE);
				}
				else
				{
					Log.e("SaveFileButton-Error", "SaveFileButton clicked in wrong state");
				}
			}
			if (arg0 == NextNoteButton)
			{
				if(state == RECORD)
				{
					pitches.add(pitch);
					Log.i("pitch added", "pitch added");
				}
				else
				{
					Log.e("NextNoteButton-Error", "NextNoteButton clicked in wrong state");
				}
			}
			
		}
		@Override
		public void onBackPressed() {

			if (state == POSTRECORD)
				{
					alert.show();
					state = PRERECORD;
				}
			
			
			else
				super.onBackPressed();
		}
}
