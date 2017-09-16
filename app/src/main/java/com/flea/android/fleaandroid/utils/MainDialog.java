package com.flea.android.fleaandroid.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flea.android.fleaandroid.R;

/**
 * Created by jerryjung on 2017. 9. 16..
 */

public class MainDialog extends Dialog {
    private final String mTitle;
    private final String mDescription;
    private final View.OnClickListener mDialogDismiss;

    public MainDialog(Context context, String title, String description, View.OnClickListener dialogDismiss) {
        super(context);
        this.mTitle = title;
        this.mDescription = description;
        this.mDialogDismiss = dialogDismiss;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_main);

        TextView mTextViewTitle = findViewById(R.id.tv_dialog_title);
        mTextViewTitle.setText(mTitle);
        TextView mTextViewDescription = findViewById(R.id.tv_dialog_description);
        mTextViewDescription.setText(mDescription);
        Button mButtonDismiss = findViewById(R.id.btn_dialog_dismiss);
        mButtonDismiss.setOnClickListener(mDialogDismiss);
    }
}
