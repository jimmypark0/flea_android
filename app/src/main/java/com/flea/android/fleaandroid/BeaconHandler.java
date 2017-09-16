package com.flea.android.fleaandroid;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by Sen on 2017. 9. 16..
 */

public class BeaconHandler extends Handler {
    FirebaseDatabase database;
    DatabaseReference myRef;

    public void handleMessage(Message msg) {
        Data data = new Data();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("beacon");

        data.timer = msg.arg1;
        data.beacon = msg.arg2;
        data.map = (HashMap) msg.obj;

        if (data.timer < 0) {

        } else {

        }

        Log.e("LOG", msg.arg1 + " // " + msg.arg2 + " //  " + msg.obj.toString());
    }
}
