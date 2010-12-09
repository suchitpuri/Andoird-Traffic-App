package com.androidMaps.screens;

/**
 * Created by IntelliJ IDEA.
 * User: suchitp
 * Date: 12/8/10
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */

import android.app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import com.android.R;
import com.androidMaps.interfaces.Screen;

public class SplashScreen extends Screen {

    protected boolean _active = true;
    protected int _splashTime = 5000; // time to display the splash screen in ms

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // thread for displaying the SplashScreen
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    dispatcher.mapScreen(SplashScreen.this);

                }
            }
        };
        splashTread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }

}