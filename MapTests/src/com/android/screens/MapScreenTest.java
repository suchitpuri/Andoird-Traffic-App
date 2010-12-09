package com.android;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.android.screens.MapScreenTest \
 * com.android.tests/android.test.InstrumentationTestRunner
 */
public class MapScreenTest extends ActivityInstrumentationTestCase2<MapScreen_bak> {

    public MapScreenTest() {
        super("com.android", MapScreen_bak.class);
    }

}
