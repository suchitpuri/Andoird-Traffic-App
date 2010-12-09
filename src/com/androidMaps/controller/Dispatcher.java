package com.androidMaps.controller;

import android.app.Activity;
import com.androidMaps.screens.SplashScreen;

/**
 * Created by IntelliJ IDEA.
 * User: suchitp
 * Date: 12/8/10
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class Dispatcher {

    ScreenController controller= new ScreenController();

    public void mapScreen(Activity activity)
    {
        controller.mapScreen(activity);
    }

}
