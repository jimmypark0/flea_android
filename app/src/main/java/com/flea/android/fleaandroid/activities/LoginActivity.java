package com.flea.android.fleaandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.estimote.sdk.SystemRequirementsChecker;
import com.flea.android.fleaandroid.R;
import com.flea.android.fleaandroid.utils.BaseActivity;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 블루투스 권한 및 활성화 코드
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
    }

    public void customOnClick(View view) {
        ((EditText) findViewById(R.id.etx_id)).setText("A-COEX");
        ((EditText) findViewById(R.id.etx_password)).setText("1234");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
