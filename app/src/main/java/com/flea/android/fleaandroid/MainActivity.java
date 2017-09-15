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

import java.util.List;

import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity {
    private BeaconManager beaconManager;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private TextView tvId;
    private Boolean isConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvId = (TextView) findViewById(R.id.tvId);

        beaconManager = new BeaconManager(getApplicationContext());
        isConnected = false;

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                Beacon currentBeacon = list.get(0);

                tvId.setText(currentBeacon.getRssi() + "");

                if(!isConnected & currentBeacon.getRssi() > -60) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                    isConnected = true;

                    dialog.setTitle("알림")
                            .setMessage("비콘이 연결되었습니다.")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .create().show();

                } else if (isConnected & currentBeacon.getRssi() <= -60) {
                    Toast.makeText(MainActivity.this, "연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
                    isConnected = false;
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