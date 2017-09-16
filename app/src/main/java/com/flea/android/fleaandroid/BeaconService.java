package com.flea.android.fleaandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.HashMap;
import java.util.List;

public class BeaconService extends Service {
    private BeaconManager beaconManager;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
    //private final IBinder mBinder = new LocalServiceBinder();

    HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
            }
        });
    }
}
