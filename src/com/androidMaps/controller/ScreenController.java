package com.androidMaps.controller;

import android.app.Activity;
import android.content.Intent;
import com.androidMaps.screens.MapScreen;

/**
 * Created by IntelliJ IDEA.
 * User: suchitp
 * Date: 12/8/10
 * Time: 11:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScreenController {
    public void mapScreen(Activity activity) {
         activity.startActivity(new Intent(activity, MapScreen.class));
    }

}
