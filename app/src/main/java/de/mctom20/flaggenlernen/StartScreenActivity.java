package de.mctom20.flaggenlernen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        if(RetainFragment.getInstance(this)!= null)
            RetainFragment.getInstance(this).destroyInstance();

        initSettings();

        //Toast.makeText(this,"oncreate called",Toast.LENGTH_SHORT).show();
    }

    public void doGoToStart(View view)
    {
        String[] checked = FlagUtils.getCheckedContinents(this);
        if(checked.length == 0)
        {
            Toast t = Toast.makeText(this,"Keine Flaggen ausgewählt!",Toast.LENGTH_LONG);
            t.show();
            return;
        }
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void doGoToSettings(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SettigsActivity.class);
        startActivity(intent);
    }

    public void doLeave(View view){
      finish();
    }

    public void initSettings()
    {
        if(FlagUtils.getFromPrefsInt(this,"Initialized",AppConsts.FAIL)==AppConsts.FAIL )
        {
            FlagUtils.setInPrefsInt(this,"Initialized",AppConsts.ON);
            Toast toast = Toast.makeText(this, "initialize settings", Toast.LENGTH_LONG);
            toast.show();
            String folders[] = FlagUtils.getContinents(this);
            for (int i=0;i<folders.length;i++)
            {
                FlagUtils.setInPrefsInt(this,folders[i],AppConsts.ON);
            }
        }
    }

}
