package de.mctom20.flaggenlernen;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class MyCheckboxListener implements CompoundButton.OnCheckedChangeListener
{
    CheckBox _cb;
    Context _context;
    public MyCheckboxListener(Context context,CheckBox cb)
    {
        _context = context;
        _cb = cb;
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String text = (String) _cb.getText();
        if(isChecked) {
            FlagUtils.setInPrefsInt(_context,text,AppConsts.ON);
        }
        else{
            FlagUtils.setInPrefsInt(_context,text,AppConsts.OFF);
        }


    }
}
