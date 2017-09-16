package com.flea.android.fleaandroid.Beacon;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import android.os.Message;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.HashMap;
import java.util.List;

import static com.flea.android.fleaandroid.utils.BaseApplicationClass.*;

/**
 * Created by Sen on 2017. 9. 16..
 */

public class BeaconThread extends Thread {
    private BeaconManager mBeaconManager;
    private long timer;

    HashMap<Integer, Boolean> mBeaconConnectMap = new HashMap<Integer, Boolean>();
    //BeaconHandler mHandler = new BeaconHandler();
    Handler mHandler;

    public BeaconThread(Handler handler, Activity activity) {
        mHandler = handler;

        mBeaconManager = new BeaconManager(activity);
        mBeaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                mBeaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
                Log.e("LOG", "mBeaconManager.startRanging()");
            }
        });

        for (int i = 0; i < ALL_ESTIMOTE_BEACONS_MINOR.length; i++) {
            mBeaconConnectMap.put(ALL_ESTIMOTE_BEACONS_MINOR[i], false);
        }

        Log.e("LOG", "BeaconThread()");
    }

    public void run() {
        mBeaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {

                for (int i = 0; i < list.size(); i++) {
                    Beacon currentBeacon = list.get(i);

                    if (!mBeaconConnectMap.get(currentBeacon.getMinor()) & currentBeacon.getRssi() > -70) {
                        mBeaconConnectMap.put(currentBeacon.getMinor(), true);
                        timer = System.currentTimeMillis();

                        Message msg = new Message();
                        msg.arg1 = -1;
                        msg.arg2 = currentBeacon.getMinor();
                        msg.obj = mBeaconConnectMap;

                        mHandler.sendMessage(msg);


                    } else if (mBeaconConnectMap.get(currentBeacon.getMinor()) & currentBeacon.getRssi() <= -70) {
                        mBeaconConnectMap.put(currentBeacon.getMinor(), false);

                        Message msg = new Message();
                        msg.arg1 = (int) (System.currentTimeMillis() - timer);
                        msg.arg2 = currentBeacon.getMinor();
                        msg.obj = mBeaconConnectMap;

                        mHandler.sendMessage(msg);
                    }
                }
            }
        });
    }
}
