package com.androidMaps.utilities;

import com.android.R;

/**
 * Created by IntelliJ IDEA.
 * User: suchitp
 * Date: 12/12/10
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 */
public enum Speed {
    SLOW(R.drawable.red),MEDUIM(R.drawable.red),FAST(R.drawable.green);
    private int icon;

    Speed(int icon)
    {

        this.icon = icon;
    }
    public int getIcon()
    {
        return icon;
    }
}
