package de.mctom20.flaggenlernen;

import android.content.Context;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by T on 01.05.2016.
 */
public class CurrentState {


    LinkedList<String> _allCheckedFlags;
    LinkedList<String> _currentQueue;
    LinkedList<String> _currentFlagSelection;
    String _currentGoal;

    public void initNewState(LinkedList<String> allCheckedFlags)
    {
        _allCheckedFlags = allCheckedFlags;
        _currentQueue = new LinkedList<String>(_allCheckedFlags);
        makeNewFlagRound();
    }


    //--------------------------
    public void makeNewFlagRound()
    {
        if(_currentQueue.size() < AppConsts.NUM_OF_BUTTONS)
            _currentQueue = new LinkedList<String>(_allCheckedFlags);
        Collections.shuffle(_currentQueue);
        int goal = new Random().nextInt(AppConsts.NUM_OF_BUTTONS);
        _currentFlagSelection = new LinkedList<String>();

        for(int i = 0; i<AppConsts.NUM_OF_BUTTONS;i++)
        {
            String el = _currentQueue.get(i);
            _currentFlagSelection.add(el);
            if(i == goal)
                _currentGoal = el;
        }
        _currentQueue.remove(_currentGoal);
    }

    public void linkToButtons(Context context, LinkedList<FlagImageButton> buttons,
                              FlagImageButton playButton)
    {
        for(int i = 0; i < AppConsts.NUM_OF_BUTTONS;i++)
        {
            String sel = _currentFlagSelection.get(i);
            FlagImageButton b = buttons.get(i);
            b.setName(sel);
            b.setGoal(sel == _currentGoal);
            b.setImageBitmap(FlagUtils.getBitmapFromAssets(context,sel + ".png"));
        }
        playButton.setName(_currentGoal);
    }
}
