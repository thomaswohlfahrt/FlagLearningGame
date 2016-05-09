package de.mctom20.flaggenlernen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Created by T on 25.04.2016.
 */
public class FlagImageButton extends ImageButton {
    public FlagImageButton(Context context) {
        super(context);
    }

    public FlagImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlagImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGoal(boolean isGoal) {
        this.isGoal = isGoal;
    }

    public boolean isGoal(){return isGoal;}

    public void playMP3(Context context)
    {
        FlagUtils.playMP3(context,name + ".mp3");
    }

    String name;
    boolean isGoal;
}
