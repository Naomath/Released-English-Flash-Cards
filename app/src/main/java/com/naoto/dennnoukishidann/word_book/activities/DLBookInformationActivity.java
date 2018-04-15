package com.naoto.dennnoukishidann.word_book.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.naoto.dennnoukishidann.word_book.classes.TextsAndNumbers;
import com.naoto.dennnoukishidann.word_book.classes.User;
import com.naoto.dennnoukishidann.word_book.downloadFragment.DLBookInformationFragment;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.processings.BundleProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;

public class DLBookInformationActivity extends AppCompatActivity implements DLBookInformationFragment.OnFinishListener {

    TextsAndNumbers data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_book);
        data = gettingIntent();
        settingFragment(data.getTexts().get(0));
        settingToolbar();
    }

    @Override
    public void finishActivity(String message) {
        backActivityWithMessage(message);
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

    public TextsAndNumbers gettingIntent() {
        //前のactivityから伝えられたbook_pathをゲットしてリターンする
        return IntentProcessing.inDLBookInformation(this);
    }

    public void settingFragment(String book_path) {
        DLBookInformationFragment fragment = new DLBookInformationFragment();
        fragment.setArguments(BundleProcessing.toDlBookInformationFrInDownload(book_path));
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
        setTitle("Download");
    }

    public void backActivity() {
        if (data.getNumbers().get(0) == 0) {
            IntentProcessing.backToMain(this, 2);
        } else {
            User user = new User(data.getTexts().get(1), data.getTexts().get(2));
            IntentProcessing.toUserInformation(this, user);
        }
    }

    public void backActivityWithMessage(String message) {
        IntentProcessing.backToMainWithMessage(this, message, 2);
    }
}
