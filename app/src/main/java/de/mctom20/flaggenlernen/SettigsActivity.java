package de.mctom20.flaggenlernen;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by T on 24.04.2016.
 */
public class SettigsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Intent intent = getIntent();

        LinearLayout ll = (LinearLayout)findViewById(R.id.settingLayout);
        String folders[] = FlagUtils.getContinents(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        for(String str : folders)
        {
            CheckBox cb = new CheckBox(this);
            cb.setLayoutParams(params);
            cb.setText(str);
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
            cb.setOnCheckedChangeListener(new MyCheckboxListener(this,cb));
            if(FlagUtils.getFromPrefsInt(this,str,AppConsts.FAIL) == AppConsts.ON)
            {
                cb.setChecked(true);
            }
            else
            {
                cb.setChecked(false);
            }
            ll.addView(cb);
        }

        //Toast.makeText(this,"oncreate called",Toast.LENGTH_SHORT).show();
    }
}

