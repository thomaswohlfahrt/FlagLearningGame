package de.mctom20.flaggenlernen;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by T on 24.04.2016.
 */
public class FlagUtils {

    public static String setFlagsToButtons(Context context,LinkedList<FlagImageButton> flagImageButtons,
                                         FlagImageButton playButton,LinkedList<String> currSel)
    {
        String goalFlag = null;
        int i = 0;
        int goal = new Random().nextInt(flagImageButtons.size());
        for(FlagImageButton b : flagImageButtons)
        {
            b.setName(currSel.get(i));
            b.setImageBitmap(FlagUtils.getBitmapFromAssets(context,currSel.get(i) + ".png"));
            b.setName(currSel.get(goal));
            b.setGoal(false);
            if(i==goal)
            {
                b.setGoal(true);
                playButton.setName(currSel.get(i));
                goalFlag = playButton.getName();
            }
            i++;
        }
        if(goalFlag != null)
            currSel.remove(goalFlag);
        else
            Toast.makeText(context, "Error: goalButton not removed from list!", Toast.LENGTH_SHORT).show();
        return goalFlag;
    }

    public static LinkedList<String> getAllCheckedCountries(Context context){
        String[] continents = getCheckedContinents(context);
        LinkedList<String> allFlags = new LinkedList<String>();
        LinkedList<String> allMP3s = new LinkedList<String>();
        for(String continent : continents) {
            String[] files = null;
            try {
                files = context.getAssets().list("Data/" + continent);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            // I assume flags and mp3s are just sorted and listed in the same order... :-)
            for (String file : files) {
                if (file.substring(file.lastIndexOf('.')).equals(".png")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Data/").append(continent).append("/").append(file.substring(0, file.lastIndexOf('.')));
                    allFlags.add(sb.toString());
                } else if (file.substring(file.lastIndexOf('.')).equals(".mp3")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Data/").append(continent).append("/").append(file.substring(0, file.lastIndexOf('.')));
                    allMP3s.add(sb.toString());
                }
            }
        }

        for(int i = 0; i<allFlags.size();i++)
        {
            if(!allFlags.get(i).equals(allMP3s.get(i)))
            {
                // damn this is bad :-<
                Toast.makeText(context,"mp3s and pngs dont align -> (" + allFlags.get(i) + ")", Toast.LENGTH_LONG).show();
            }
        }
        Toast.makeText(context,"Anzahl der ausgewählten Flaggen: " + String.valueOf(allFlags.size()),Toast.LENGTH_SHORT).show();
        return allFlags;
    }

    public static String getFromPrefsStr(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(AppConsts.PREFS,context.MODE_PRIVATE);
        return prefs.getString(key, null);
    }
    public static int getFromPrefsInt(Context context,String key,int defaultVal)
    {
        SharedPreferences prefs = context.getSharedPreferences(AppConsts.PREFS,context.MODE_PRIVATE);
        return prefs.getInt(key, defaultVal);
    }

    public static void setInPrefsStr(Context context, String key, String val)
    {

        SharedPreferences.Editor editor = context.getSharedPreferences(AppConsts.PREFS,context.MODE_PRIVATE).edit();
        editor.putString(key, val);
        editor.commit(); // editor.apply();
    }

    public static void setInPrefsInt(Context context, String key, int val)
    {

        SharedPreferences.Editor editor = context.getSharedPreferences(AppConsts.PREFS,context.MODE_PRIVATE).edit();
        editor.putInt(key, val);
        editor.commit(); // editor.apply();
    }

    public static void playMP3inMS(Context context, String mp3FileName, int ms)
    {
        Thread t = new Thread(new Mp3Player(context,mp3FileName,ms));
        t.start();
    }

    public static void playMP3(Context context, String mp3FileName)
    {
        try {
            MediaPlayer player = new MediaPlayer();
            AssetFileDescriptor afd = context.getAssets().openFd(mp3FileName);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Bitmap getBitmapFromAssets(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream instr = null;
        Bitmap bitmap = null;
        try {
            instr = assetManager.open(fileName);
            bitmap = BitmapFactory.decodeStream(instr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static String[] getContinents(Context context)
    {
        String[] folders = null;
        try {
            AssetManager assets = context.getAssets();
            folders = assets.list("Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return folders;
    }

    public static String[] getCheckedContinents(Context context)
    {
        String[] folders = null;
        ArrayList<String> checked = new ArrayList<String>();
        try {
            AssetManager assets = context.getAssets();
            String[] temp = assets.list("Data");
            for(String folder : temp)
            {
                if(getFromPrefsInt(context,folder,AppConsts.FAIL) == AppConsts.ON )
                {
                    checked.add(folder);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        folders = new String[checked.size()];
        checked.toArray(folders);
        return folders;
    }

}
