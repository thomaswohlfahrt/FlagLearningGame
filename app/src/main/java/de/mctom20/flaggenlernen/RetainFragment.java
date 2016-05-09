package de.mctom20.flaggenlernen;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by T on 30.04.2016.
 */
public class RetainFragment extends Fragment {

    CurrentState _currentState;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true );
    }

    static RetainFragment _instance = null;
    public static RetainFragment getInstance(Context t)
    {
        if(_instance != null) {
            //Toast.makeText(t,"already there",Toast.LENGTH_SHORT).show();
            return _instance;
        }

        //Toast.makeText(t,"new instance",Toast.LENGTH_SHORT).show();
        _instance = new RetainFragment();
        return _instance;
    }
    public void destroyInstance()
    {
        _instance = null;
    }

    public CurrentState getCurrentState()
    {
        return _currentState;
    }
    public void setCurrentState(CurrentState cs)
    {
        _currentState = cs;
    }
}
