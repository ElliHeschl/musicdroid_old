package at.tugraz.musicdroid.dialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import at.tugraz.musicdroid.DrumsActivity;
import at.tugraz.musicdroid.R;


public class SavePresetDialog extends DialogFragment {
	private EditText editText = null;
	private static final String DEFAULT_FILENAME_TIME_FORMAT = "yyyy_mm_dd_hhmmss";
	private static final String FTYPE = ".xml";
	private Context context = null;
	private String filename = null;
	
	public SavePresetDialog() {
	}

	
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.save_preset_menu, null);
        builder.setView(view);
        
        builder.setTitle(R.string.dialog_save_preset_title)
               .setNegativeButton(R.string.dialog_save, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   filename = editText.getText()+"";
                	   ((DrumsActivity)getActivity()).saveCurrentPreset(filename);
                   }
               })
               .setPositiveButton(R.string.dialog_abort, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });
        
        
        AlertDialog dialog = builder.create();
        
        String filename = getDefaultFileName();
        editText = (EditText) view.findViewById(R.id.dialog_edittext);
        editText.setText(filename);
        
        return dialog;
    }
    
	private String getDefaultFileName() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DEFAULT_FILENAME_TIME_FORMAT);
		return "preset_" + simpleDateFormat.format(new Date());
	}
	
	public String getFilename()
	{
		return filename;
	}
    
}
