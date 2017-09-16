package com.flea.android.fleaandroid.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flea.android.fleaandroid.R;
import com.flea.android.fleaandroid.utils.BaseActivity;
import com.flea.android.fleaandroid.utils.MainDialog;
import com.tistory.dwfox.dwrulerviewlibrary.view.DWRulerSeekbar;
import com.tistory.dwfox.dwrulerviewlibrary.view.ScrollingValuePicker;

import jp.wasabeef.blurry.Blurry;

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
    private TextView mTextViewDescription, mTextViewRecommend1, mTextViewRecommend2, mTextViewRecommend3;

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

        TextView textViewTitle = findViewById(R.id.tv_main_title);
        mTextViewDescription = findViewById(R.id.tv_main_description);
        TextView textViewHashTags = findViewById(R.id.tv_main_hash_tags);
        textViewTitle.setText("우아한 형제들");
        mTextViewDescription.setText("‘좋은 음식을 먹고 싶은 곳에서’ 라는\n" +
                "비전 아래 배달의민족, 배민라이더스, 배민찬 등을 서비스하며 종합 ‘푸드테크’ 기업으로\n" +
                "나아가고 있습니다.\n" +
                "\n" +
                "우리는 ‘구성원을 행복하게 만들면 행복한 구성원이 더 좋은 서비스를 만든다’ 는 믿음으로 성장의 중심에는 코드 덩어리가 아닌 \u0003가치를 만들고 스스로 가치를 높이며 일하는\n" +
                "우아한 개발자들이 있습니다.");
        textViewHashTags.setText("#개발 #디자인 #기획 #해커톤 #오픈소스");

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


        ScrollingValuePicker myScrollingValuePicker = findViewById(R.id.myScrollingValuePicker);
        myScrollingValuePicker.setViewMultipleSize(0.9f);
        myScrollingValuePicker.setMaxValue(0, 10);
        myScrollingValuePicker.setValueTypeMultiple(1);
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