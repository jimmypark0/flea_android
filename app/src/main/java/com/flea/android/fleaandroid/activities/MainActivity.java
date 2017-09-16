package com.flea.android.fleaandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flea.android.fleaandroid.Beacon.BeaconThread;
import com.flea.android.fleaandroid.R;
import com.flea.android.fleaandroid.utils.BaseActivity;
import com.flea.android.fleaandroid.utils.MainDialog;
import com.tistory.dwfox.dwrulerviewlibrary.view.ScrollingValuePicker;
import com.flea.android.fleaandroid.utils.BaseApplicationClass;

public class MainActivity
        extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private MainDialog mMainDialog;
    private final View.OnClickListener dialogButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
            mMainDialog.dismiss();
        }
    };
    private Boolean isFabOpen = false;
    private TextView mTextViewTitle, mTextViewDescription, mTextViewHashTags,
            mTextViewRecommend1, mTextViewRecommend2, mTextViewRecommend3;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_map:
                startActivity(new Intent(this, MapActivity.class));
                return true;
            case R.id.action_event_list:
                Toast.makeText(getApplicationContext(), "Clicked 이벤트들", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_vibration:
                Toast.makeText(getApplicationContext(), "Clicked 진 동", Toast.LENGTH_SHORT).show();
                item.setTitle("진 동  X");
                return true;
            case R.id.action_notification:
                Toast.makeText(getApplicationContext(), "Clicked 알 림", Toast.LENGTH_SHORT).show();
                item.setTitle("알 람 X");
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

        mTextViewTitle = findViewById(R.id.tv_main_title);
        mTextViewDescription = findViewById(R.id.tv_main_description);
        mTextViewHashTags = findViewById(R.id.tv_main_hash_tags);

        mTextViewRecommend1 = findViewById(R.id.tv_recommend_1);
        mTextViewRecommend2 = findViewById(R.id.tv_recommend_2);
        mTextViewRecommend3 = findViewById(R.id.tv_recommend_3);

        dismissFabChild();

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFabOpen) {
                    showFabChild();
                } else {
                    dismissFabChild();
                }
            }
        });

        mMainDialog = new MainDialog(this, BaseApplicationClass.TITLE, BaseApplicationClass.ITRO,
                dialogButtonListener);
        mMainDialog.show();


        final ScrollingValuePicker myScrollingValuePicker = findViewById(R.id.myScrollingValuePicker);
        myScrollingValuePicker.setViewMultipleSize(0.9f);
        myScrollingValuePicker.setMaxValue(0, 10);
        myScrollingValuePicker.setValueTypeMultiple(1);
        myScrollingValuePicker.setInitValue(4);

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                //database = FirebaseDatabase.getInstance();
                //myRef = database.getReference("beacon");

                if (msg.arg1 < 0) {
                    for (int i = 0; i < BaseApplicationClass.ALL_ESTIMOTE_BEACONS_MINOR.length; i++) {
                        if (msg.arg2 == BaseApplicationClass.ALL_ESTIMOTE_BEACONS_MINOR[i]) {
                            findViewById(R.id.tv_main_intro).setVisibility(View.GONE);
                            mTextViewTitle.setText(BaseApplicationClass.TextViewTitle[i]);
                            mTextViewDescription.setText(BaseApplicationClass.TextViewDescription[i]);
                            mTextViewHashTags.setText(BaseApplicationClass.TextViewHashTags[i]);
                        }
                    }
                } else {
                    Log.e("LOG", "msg.arg1 is bigger than 0");
                }

                Log.e("LOG", msg.arg1 + " // " + msg.arg2 + " //  " + msg.obj.toString());
            }
        };

        new BeaconThread(handler, this).start();
    }

    private void showFabChild() {
        mTextViewDescription.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        (findViewById(R.id.rl_layout_main)).bringToFront();
        isFabOpen = false;
        mTextViewRecommend1.animate().setDuration(100).scaleX(1);
        mTextViewRecommend2.animate().setDuration(200).scaleX(1);
        mTextViewRecommend3.animate().setDuration(300).scaleX(1);
    }

    private void dismissFabChild() {
        mTextViewDescription.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        isFabOpen = true;
        mTextViewRecommend1.animate().setDuration(300).scaleX(0);
        mTextViewRecommend2.animate().setDuration(200).scaleX(0);
        mTextViewRecommend3.animate().setDuration(100).scaleX(0);
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