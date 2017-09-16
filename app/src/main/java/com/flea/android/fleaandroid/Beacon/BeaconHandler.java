package com.flea.android.fleaandroid.Beacon;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static android.R.attr.data;

/**
 * Created by Sen on 2017. 9. 16..
 */

public class BeaconHandler extends Handler {
    FirebaseDatabase database;
    DatabaseReference myRef;

    public void handleMessage(Message msg) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("beacon");

        Log.e("LOG", msg.arg1 + " // " + msg.arg2 + " //  " + msg.obj.toString());
    }
}
