package com.flea.android.fleaandroid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.HashMap;
import java.util.List;

public class BeaconService extends Service {
    public class MainServiceBinder extends Binder {
        BeaconService getService() {
            return BeaconService.this;
        }
    }

    private BeaconManager beaconManager;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
    private static final int beaconMinors[] = {38547, 16501, 978};
    private final IBinder mBinder = new MainServiceBinder();

    HashMap<Integer, Boolean> beaconConnectMap = new HashMap<Integer, Boolean>();
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
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

        for (int i = 0; i < beaconMinors.length; i++) {
            beaconConnectMap.put(beaconMinors[i], false);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                for (int i = 0; i < list.size(); i++) {

                    Beacon currentBeacon = list.get(i);

                    if (!beaconConnectMap.get(currentBeacon.getMinor()) & currentBeacon.getRssi() > -70) {
                        beaconConnectMap.put(currentBeacon.getMinor(), true);

                    } else if (beaconConnectMap.get(currentBeacon.getMinor()) & currentBeacon.getRssi() <= -70) {
                        beaconConnectMap.put(currentBeacon.getMinor(), false);
                    }
                }
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
