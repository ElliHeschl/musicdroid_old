package at.tugraz.ist.musicdroid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;
  
/** 
 * helper for Prompt-Dialog creation 
 */  
public abstract class PromptDialog extends AlertDialog.Builder implements OnClickListener {  
 private final EditText input;  
 private String tag;  
 private String result;

  
 /** 
  * @param context 
  * @param title resource id 
  * @param message resource id 
  */  
 public PromptDialog(Context context, String title, String message) {  
  super(context);  
  setTitle(title);  
  setMessage(message);  
  
  input = new EditText(context);  
  setView(input);  
  
  setPositiveButton("OK", this); //R.string.ok 
  setNegativeButton("Cancel", this);  //R.string.cancel
 }  
 
 public PromptDialog(Context context, String title, String message, String tag) {  
	 this(context, title, message);
	 this.tag = tag;
	 }  
 
  
 /** 
  * will be called when "cancel" pressed. 
  * closes the dialog. 
  * can be overridden. 
  * @param dialog 
  */  
 public void onCancelClicked(DialogInterface dialog) {  
  dialog.dismiss();  
 }  
  
 public void onClick(DialogInterface dialog, int which) {  
  if (which == DialogInterface.BUTTON_POSITIVE) { 
	  result = input.getText().toString();
   if (onOkClicked(result, tag)) {  
    dialog.dismiss();
   }  
  } else {  
   onCancelClicked(dialog);  
  }  
 }  
  
 /** 
  * called when "ok" pressed. 
  * @param input 
  * @return true, if the dialog should be closed. false, if not. 
  */  
 
 abstract public boolean onOkClicked(String input, String tag);  
}  