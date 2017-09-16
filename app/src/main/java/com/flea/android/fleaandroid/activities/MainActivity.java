package com.flea.android.fleaandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.flea.android.fleaandroid.R;
import com.flea.android.fleaandroid.utils.BaseActivity;
import com.flea.android.fleaandroid.utils.MainDialog;

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

        mMainDialog = new MainDialog(this,
                "2017 취업박람회",
                "35년간 유학업계를 선도하며 \n" +
                        "변함없이 안심유학의 길을 걸어온 종로유학원이 \n" +
                        "오는 9월 16일(토)에 코엑스에서 \n" +
                        "2017 해외유학박람회를 개최합니다.\n" +
                        "많은 우수 학교들이 참가하는 이번 박람회를 통해 어학연수, 해외대학 진학, 초중고 유학 등 다양한 프로그램 정보를 만나실 수 있습니다.\n" +
                        "\n" +
                        "총 10개국 어학연수 및 유학 상담이 가능합니다. 국가별 전문가와 1:1 상담을 받아보세요.",
                dialogButtonListener);
        mMainDialog.show();
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