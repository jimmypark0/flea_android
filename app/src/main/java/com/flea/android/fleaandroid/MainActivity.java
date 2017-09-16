package com.flea.android.fleaandroid;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.HashMap;
import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity {
    private BeaconManager beaconManager;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);
    private static int minors[] = {38547, 16501, 978};
    HashMap<Integer, Boolean> connectCheckMap = new HashMap<Integer, Boolean>();

    private TextView tvId;
    private long timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvId = (TextView) findViewById(R.id.tvId);
        beaconManager = new BeaconManager(getApplicationContext());

        for(int i = 0; i < minors.length; i++) {
            connectCheckMap.put(minors[i], false);
        }

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                for (int i = 0; i < list.size(); i++) {

                    Beacon currentBeacon = list.get(i);
                    tvId.setText(currentBeacon.getRssi() + " / Minor : " + currentBeacon.getMinor());
                    tvId.append("\n" + connectCheckMap);

                    if (!connectCheckMap.get(currentBeacon.getMinor()) & currentBeacon.getRssi() > -70) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                        connectCheckMap.put(currentBeacon.getMinor(), true);
                        timer = System.currentTimeMillis();

                        dialog.setTitle("알림")
                                .setMessage(currentBeacon.getMinor() + " 비콘이 연결되었습니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Do Nothing
                                    }
                                })
                                .create().show();

                    } else if (connectCheckMap.get(currentBeacon.getMinor()) & currentBeacon.getRssi() <= -70) {
                        timer = System.currentTimeMillis() - timer;
                        Toast.makeText(MainActivity.this, currentBeacon.getMinor() + " 비콘 연결이 끊어졌습니다. 연결시간(ms) : " + timer, Toast.LENGTH_SHORT).show();
                        connectCheckMap.put(currentBeacon.getMinor(), false);
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 블루투스 권한 및 활성화 코드
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS_REGION);
        super.onPause();
    }
}