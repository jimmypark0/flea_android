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

import static com.flea.android.fleaandroid.BaseApplicationClass.*;

public class BeaconService extends Service {
    public class MainServiceBinder extends Binder {
        BeaconService getService() {
            return BeaconService.this;
        }
    }

    private BeaconManager mBeaconManager;
    private final IBinder mBinder = new MainServiceBinder();
    HashMap<Integer, Boolean> mBeaconConnectMap = new HashMap<Integer, Boolean>();

    @Override
    public IBinder onBind(Intent intent) {
        mBeaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                for (int i = 0; i < list.size(); i++) {
                    Beacon currentBeacon = list.get(i);

                    if (!mBeaconConnectMap.get(currentBeacon.getMinor()) & currentBeacon.getRssi() > -70) {
                        mBeaconConnectMap.put(currentBeacon.getMinor(), true);

                    } else if (mBeaconConnectMap.get(currentBeacon.getMinor()) & currentBeacon.getRssi() <= -70) {
                        mBeaconConnectMap.put(currentBeacon.getMinor(), false);
                    }
                }
            }
        });
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBeaconManager = new BeaconManager(getApplicationContext());
        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                mBeaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                Log.e("LOG", "startRanging()");
            }
        });

        for (int i = 0; i < ALL_ESTIMOTE_BEACONS_MINOR.length; i++) {
            mBeaconConnectMap.put(ALL_ESTIMOTE_BEACONS_MINOR[i], false);
        }

        Log.e("LOG", "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
