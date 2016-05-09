package de.mctom20.flaggenlernen;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by T on 26.04.2016.
 */
public class Mp3Player implements Runnable{

    Mp3Player(Context context,String mp3File,int waitMS)
    {
        this.context = context;
        this.mp3File = mp3File;
        this.watiMS = waitMS;
    }
    Context context;
    String mp3File;
    int watiMS;

    @Override
    public void run() {
        try {
            Thread.sleep(watiMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            MediaPlayer player = new MediaPlayer();
            AssetFileDescriptor afd = context.getAssets().openFd(mp3File);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
