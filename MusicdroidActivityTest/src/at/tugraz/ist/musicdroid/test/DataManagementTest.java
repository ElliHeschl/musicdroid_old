package at.tugraz.ist.musicdroid.test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;
import at.tugraz.ist.musicdroid.MidiFile;
import at.tugraz.ist.musicdroid.NoteMapper;
import at.tugraz.ist.musicdroid.SoundPlayer;
import at.tugraz.ist.musicdroid.common.DataManagement;
import at.tugraz.ist.musicdroid.common.Projekt;


public class DataManagementTest extends AndroidTestCase {
	
	private DataManagement dm = null;
	private static final char[] ILLEGAL_CHARACTERS = {'\n', '\r', '\t', '\0', '\f', '?', '*', '\\', '<', '>', '|', '\"', ':', '/', '`'};
	
	@Override
	protected void setUp() throws Exception {
		dm = new DataManagement();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}	
	
	
	/**
	 * Asserts that directory specified by path does not exist
	 * @param path Path to directory
	 */
	private void assertIsNoDirectory(String path)
	{
		File f=new File(path);  
		assertFalse("Path: " + path + " exists", f.exists());
		assertFalse("Path: " + path + " is a Directory", f.isDirectory());
	}
	
	private void assertFileExists(String path)
	{
		File f=new File(path);  
		assertTrue("Path: " + path + "does not exist", f.exists());		
	}
	
	private void assertFileDoesNotExist(String path)
	{
		File f=new File(path);  
		assertFalse("Path: " + path + "exists", f.exists());		
	}
	
	/**
	 * Asserts that directory specified by path exists
	 * @param path Path to directory
	 */	
	private void assertIsDirectory(String path)
	{
		File f = new File(path);
		assertTrue("Path: " + path + " is no Directory", f.isDirectory());
    }

	/**
	 * Deletes all created directories or files
	 * @param path Path to directory
	 */
	private void cleanUp(String path)
	{
		File directory = new File(path);
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    cleanUp(files[i].getPath());
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    directory.delete();
	}
	
	/**
	 * Helper for TC testCopyFile, asserts a given file is empty
	 * @param path Path to file
	 */
	private void assertFileIsEmpty(String path)
	{
		BufferedReader b;
		try {
			b = new BufferedReader(new FileReader(path));
			assertTrue(b.readLine() == null );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper for TC testCopyFile, checks if given file contains given string (must be first line in file)
	 * @param path Path to file that should be checked
	 * @param str String that should be contained within the file
	 */
	private void assertFileContainsString(String path, String str)
	{
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(path);		  
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null)   {
		      assertEquals(str, strLine);
		    }
			in.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper for TC testCopyFile, writes given string to a given file
	 * @param path Path to file the string should be written to
	 * @param str String that should be written to the file
	 */
	private void writeStringToFile(String path, String str)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(path, "UTF-8");
			writer.println(str);
			writer.close();
		} catch (FileNotFoundException e) {
			assertTrue("File Not Found", false);
		} catch (UnsupportedEncodingException e) {
			assertTrue("Unsupported Encoding", false);
		}
	}
	
	
	/**
	 * Tests if directory specified by a path to a file is created
	 */
	public void testCheckDirectoryFilePath()
	{
		String path = "" + Environment.getExternalStorageDirectory() + "/does/not/exist.txt";
		String check_path = "" + Environment.getExternalStorageDirectory() + "/does/not/";
		assertIsNoDirectory(path);
		dm.checkDirectory(path);
		assertIsDirectory(check_path);
		cleanUp(check_path);
	}
	
	
	/**
	 * Tests if new directory specified by a path is created 
	 * FAILS!
	 */
	public void testCheckDirectoryDirPath()
	{
		String path = "" + Environment.getExternalStorageDirectory() + "/does/not/exist/";
		assertIsNoDirectory(path);
		dm.checkDirectory(path);
		assertIsDirectory(path);
		cleanUp(path);		
	}
	
	
	/**
	 * Tests if new directory specified by a path containing special characters is created
	 */
	public void testCheckDirectorySpecialCharacter()
	{
		String path = "" + Environment.getExternalStorageDirectory() + "/does/@%%/exist.txt";
		String check_path = "" + Environment.getExternalStorageDirectory() + "/does/@%%/";
		assertIsNoDirectory(path);
		dm.checkDirectory(path);
		assertIsDirectory(check_path);
		cleanUp(check_path);
	}
	
	
	/**
	 * Tests if directory specified by an INVALID path is NOT created
	 * ERROR: path: "test///exists.txt" and "test/'/exists.txt" created!    
	 */
	public void testCheckDirectoryInvalidPath()
	{
		for(int i = 0; i < ILLEGAL_CHARACTERS.length; i++)
		{
			String path = "" + Environment.getExternalStorageDirectory() + "/test/" + ILLEGAL_CHARACTERS[i] + "/exist.txt";
			String check_path = "" + Environment.getExternalStorageDirectory() + "/test/"+ ILLEGAL_CHARACTERS[i] + "/";
			assertIsNoDirectory(path);
			dm.checkDirectory(path);
			assertIsNoDirectory(check_path);
			cleanUp(check_path);
		}
	}
	
	/**
	 * Test CopyFile function
	 * Is it necessary to check if copyFile overwrites existing files?  
	 */
	public void testCopyFile()
	{
		String path_in = "" + Environment.getExternalStorageDirectory() + "/test/in.txt";
		String path_out = "" + Environment.getExternalStorageDirectory() + "/test/out.txt";
		dm.checkDirectory(path_in);
		
		writeStringToFile(path_in, "TestLine");
		assertFileContainsString(path_in, "TestLine");
		assertFileIsEmpty(path_out);
		
		try {
			dm.copyFile(new File(path_in), new File(path_out));
		} catch (IOException e) {
			assertTrue("Could not Copy File", false);
		} 
		
		assertFileContainsString(path_out, "TestLine");
		cleanUp("" + Environment.getExternalStorageDirectory() + "/test/");
	}
	
	public void testDeleteFile()
	{
		String path = "" + Environment.getExternalStorageDirectory() + "/test/in.txt";
		dm.checkDirectory(path);		
		writeStringToFile(path, "TestLine");
		assertFileExists(path);
		dm.deleteFile(path);
		assertFileDoesNotExist(path);
		cleanUp("" + Environment.getExternalStorageDirectory() + "/test/");
	}
	
	public void testDeleteFilePathIsDirectory()
	{
		String path = "" + Environment.getExternalStorageDirectory() + "/test/dont/delete/me/";
		cleanUp(path);
		dm.checkDirectory(path);		
		assertIsDirectory(path);
		dm.deleteFile(path);
		assertIsDirectory(path);
		cleanUp(path);
	}
	
	public void testLoadSoundFile()
	{
		int number_of_files = Projekt.imported_files_.size();
		String path = "" + Environment.getExternalStorageDirectory() + "/test/in.mp3";
		dm.checkDirectory(path);
		dm.LoadSoundFile(path);
		assert(Projekt.imported_files_.size() > number_of_files);
		cleanUp(path);
	}
}
