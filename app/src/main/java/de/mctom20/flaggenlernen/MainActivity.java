package de.mctom20.flaggenlernen;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    LinkedList<FlagImageButton> buttons;
    FlagImageButton playButton;
    RetainFragment rf;

    Activity thisActivity;
    volatile long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_port);

        buttons = new LinkedList<FlagImageButton>();
        buttons.add((FlagImageButton) findViewById(R.id.imageButton1));
        buttons.add((FlagImageButton) findViewById(R.id.imageButton2));
        buttons.add((FlagImageButton) findViewById(R.id.imageButton3));
        buttons.add((FlagImageButton) findViewById(R.id.imageButton4));
        buttons.add((FlagImageButton) findViewById(R.id.imageButton5));
        buttons.add((FlagImageButton) findViewById(R.id.imageButton6));
        playButton = (FlagImageButton) findViewById(R.id.imageButton_play);

        rf = RetainFragment.getInstance(this);

        if(rf.getCurrentState() == null)
        {
            CurrentState cs = new CurrentState();
            cs.initNewState(FlagUtils.getAllCheckedCountries(this));
            rf.setCurrentState(cs);
            rf.getCurrentState().linkToButtons(this,buttons,playButton);
            FlagUtils.playMP3inMS(this, playButton.getName() + ".mp3",1500);
        }
        else
        {
            rf.getCurrentState().linkToButtons(this,buttons,playButton);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public void flagButtonClicked(View v)
    {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2500)
            return;
        mLastClickTime = SystemClock.elapsedRealtime();

        FlagImageButton ib = (FlagImageButton)v;
        if (ib.isGoal()) {
            // richtig
            Toast.makeText(this, "Richtig", Toast.LENGTH_SHORT).show();
            FlagUtils.playMP3(this, "etc/richtig.mp3");


            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            rf.getCurrentState().makeNewFlagRound();
            rf.getCurrentState().linkToButtons(this,buttons,playButton);

        } else {
            FlagUtils.playMP3(this, "etc/falsch.mp3");
            Toast.makeText(this, "Das war " + ib.getName().substring(ib.getName().lastIndexOf('/') + 1), Toast.LENGTH_SHORT).show();
        }
        FlagUtils.playMP3inMS(this, playButton.getName() + ".mp3", 1500);

    }

    public void playButtonClicked(View v)
    {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500)
            return;
        mLastClickTime = SystemClock.elapsedRealtime();
        FlagImageButton ib = (FlagImageButton)v;
        FlagUtils.playMP3(this,ib.getName() + ".mp3");
    }

    @Override
    public void onBackPressed() {
        rf.destroyInstance();
        super.onBackPressed();
    }
}
