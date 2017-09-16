package com.flea.android.fleaandroid;

import android.app.Application;

import com.estimote.sdk.Region;

/**
 * Created by jerryjung on 2017. 9. 16..
 */

public class BaseApplicationClass extends Application {
    static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
    static final int ALL_ESTIMOTE_BEACONS_MINOR[] = {38547, 16501, 978};
}
