package at.tugraz.ist.musicdroid.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Vector;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class DataManagement {

	public void copyFile(File input, File output) throws IOException {
		checkDirectory(output.getPath());
		try {
			checkDirectory(output.getPath());

			FileChannel inChannel = new FileInputStream(input).getChannel();
			FileChannel outChannel = new FileOutputStream(output).getChannel();

			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	public void checkDirectory(String directory_path) {
		File directory;
		String new_path = "";
		String[] splittArray = directory_path.split("/");
		int length = splittArray.length;
		if(!directory_path.endsWith("/")) length -= 1; 
		  
		for (int i = 0; i < length; i++) {
			new_path += splittArray[i] + "/";
			directory = new File(new_path);
			if (!(directory.exists() && directory.isDirectory() && directory
					.canWrite())) {
				directory.mkdir();
			}
		}
	}
	
	//Changed from LoadSoundFile(Intent data) to LoadSoundFile(String path)
	public void LoadSoundFile(String path) {
		try {
			File input = new File(path);
			File output = new File(Constants.MAIN_DIRECTORY
					+ Constants.SOUND_FILE_SUB_DIRECTORY + input.getName());

			copyFile(input, output);

			Projekt.getInstance().addSoundFile(output.getAbsolutePath());
			System.out.println(output.getAbsolutePath());
			System.out.println(Projekt.getInstance().getLastSoundFile());

		} catch (Exception e) {
		}
	};

	public void deleteFile(String filename) {
		File file = new File(filename);
		if(file.isDirectory()) return;
		file.delete();
	}

	public Vector<String> LoadDirectory(String directoryPath) {
		return new Vector<String>();
	}

	public Vector<String> LoadXML(String filename) {
		return new Vector<String>();
	}
}
