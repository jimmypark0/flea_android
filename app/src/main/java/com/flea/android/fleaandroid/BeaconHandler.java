package com.flea.android.fleaandroid;


import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import static com.estimote.sdk.EstimoteSDK.getApplicationContext;

/**
 * Created by Sen on 2017. 9. 16..
 */

public class BeaconHandler extends Handler {
    public void handleMessage(Message msg) {
        Log.e("LOG", "" + msg.obj.toString());
    }
}
