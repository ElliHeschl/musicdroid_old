package at.tugraz.musicdroid.drums;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.os.Environment;

public class DrumPresetHandler {
	public final static String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/presets/";
	Context context = null;
	HashMap<Integer, DrumPreset> presetMap = null;
	
	public DrumPresetHandler(Context c)
	{
		context = c;
		presetMap = new HashMap<Integer, DrumPreset>();
		checkPathExistsAndCreateDirectory();
	}
	
	public void writeDrumLoopToPreset(String name, ArrayList<DrumSoundRow> drumSoundRowsArray)
	{
		Serializer serializer = new Persister();
		DrumPreset test = new DrumPreset(name, drumSoundRowsArray);
		checkPathExistsAndCreateDirectory();
		File result = new File(path+"/" + name + ".xml");
		
		
		try {
			serializer.write(test, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DrumPreset readPresentByName(String name)
	{
		Serializer serializer2 = new Persister();
		String filename = path+"/"+name;
		if(!filename.endsWith(".xml")) filename = filename + ".xml";
		File source = new File(filename);

		try {
			DrumPreset preset = serializer2.read(DrumPreset.class, source);
			return preset;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void checkPathExistsAndCreateDirectory()
	{
		File f = new File(path);
		if(!f.exists())
			f.mkdir();
	}
	
	public void writeTestPreset()
	{ 
		Serializer serializer = new Persister();
		DrumPreset test = new DrumPreset();
		File result = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.xml");

		try {
			serializer.write(test, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Serializer serializer2 = new Persister();
		File source = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.xml");

		try {
			DrumPreset example = serializer2.read(DrumPreset.class, source);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
