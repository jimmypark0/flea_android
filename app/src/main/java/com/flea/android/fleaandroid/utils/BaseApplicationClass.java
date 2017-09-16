package com.flea.android.fleaandroid.utils;

import android.app.Application;

import com.estimote.sdk.Region;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by jerryjung on 2017. 9. 16..
 */

public class BaseApplicationClass extends Application {
    public static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
    public static final int ALL_ESTIMOTE_BEACONS_MINOR[] = {38547, 16501, 978};

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NanumGothic.otf")
                .build());
    }
}
