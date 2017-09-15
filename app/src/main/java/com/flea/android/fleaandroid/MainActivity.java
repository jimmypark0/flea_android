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

import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity {
    private BeaconManager beaconManager;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private TextView tvId;
    HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvId = (TextView) findViewById(R.id.tvId);

        beaconManager = new BeaconManager(getApplicationContext());

        map.put(38547, false);
        map.put(16501, false);
        map.put(978, false);

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
                    tvId.append("\n" + map);

                    if (!map.get(currentBeacon.getMinor()) & currentBeacon.getRssi() > -70) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                        map.put(currentBeacon.getMinor(), true);

                        dialog.setTitle("알림")
                                .setMessage(currentBeacon.getMinor() + " 비콘이 연결되었습니다.")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Test
                                    }
                                })
                                .create().show();

                    } else if (map.get(currentBeacon.getMinor()) & currentBeacon.getRssi() <= -70) {
                        Toast.makeText(MainActivity.this, currentBeacon.getMinor() + " 비콘 연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
                        map.put(currentBeacon.getMinor(), false);
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