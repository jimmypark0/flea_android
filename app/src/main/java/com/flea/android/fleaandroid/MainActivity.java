package com.flea.android.fleaandroid;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.estimote.sdk.SystemRequirementsChecker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity
        extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Data data = new Data();

            //database = FirebaseDatabase.getInstance();
            //myRef = database.getReference("beacon");

            data.timer = msg.arg1;
            data.beacon = msg.arg2;
            data.map = (HashMap) msg.obj;

            if (data.timer < 0) {
                startActivity(new Intent(getApplicationContext(), SearchListActivity.class));
            } else {

            }

            Log.e("LOG", msg.arg1 + " // " + msg.arg2 + " //  " + msg.obj.toString());
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                Toast.makeText(getApplicationContext(), "Clicked MAP", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_event_list:
                Toast.makeText(getApplicationContext(), "Clicked 이벤트들", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_vibration:
                Toast.makeText(getApplicationContext(), "Clicked 진 동", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_notification:
                Toast.makeText(getApplicationContext(), "Clicked 알 림", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }

        mDrawerLayout = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView leftNavigationView = findViewById(R.id.left_navigation);
        leftNavigationView.setNavigationItemSelectedListener(this);

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        new BeaconThread(mHandler, this).start();
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }

        super.onBackPressed();
    }

    // 검색화면으로 이동하는 작업
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(new Intent(getApplicationContext(), SearchListActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

