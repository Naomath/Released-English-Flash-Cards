package com.naoto.dennnoukishidann.word_book.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.classes.TextsAndNumbers;
import com.naoto.dennnoukishidann.word_book.processings.BundleProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;
import com.naoto.dennnoukishidann.word_book.shareFragment.ULBookInformationFragment;

public class ULBookInformationActivity extends AppCompatActivity implements ULBookInformationFragment.OnFinishListener {

    TextsAndNumbers data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_book);
        gettingIntent();
        settingFragment();
        settingToolbar();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタンの処理
            backActivity();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void finishActivity(String message) {
        backActivityWithMessage(message);
    }

    public void gettingIntent() {
        data = IntentProcessing.fromAboutMeInUlInformation(this);
    }

    public void settingFragment() {
        ULBookInformationFragment fragment = new ULBookInformationFragment();
        fragment.setArguments(BundleProcessing.toULBookInformationFr(data));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.relative, fragment);
        transaction.commit();
    }

    public void settingToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });
        setTitle(data.getTexts().get(0));
    }

    public void backActivity() {
        IntentProcessing.backToMain(this, 2);
    }

    public void backActivityWithMessage(String message) {
        IntentProcessing.backToMainWithMessage(this, message, 2);
    }

}
