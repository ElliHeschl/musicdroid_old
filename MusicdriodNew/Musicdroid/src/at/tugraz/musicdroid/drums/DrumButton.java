package at.tugraz.musicdroid.drums;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageButton;
import at.tugraz.musicdroid.R;

public class DrumButton extends ImageButton {
	private int position = 0;
	private Drawable drumButtonUnclicked = null;
		
	public DrumButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public DrumButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public DrumButton(Context context) {
		super(context);
	}
	
	private void init(AttributeSet attrs)
	{
		TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DrumButton);
		this.position = a.getInteger(R.styleable.DrumButton_position, 0);
		a.recycle();
	}
	
	public void changeDrawableOnClick(int beat)
	{
		if(beat == 1)
		{
			if(drumButtonUnclicked == null) drumButtonUnclicked = getDrawable();
			setImageDrawable(getResources().getDrawable(R.drawable.drum_button_clicked));
		}
		else
		{
			setImageDrawable(drumButtonUnclicked);
		}
	}
	
	public void setPosition(int pos)
	{
		position = pos;
	}
	
	public int getPosition()
	{
		return position;
	}

}
