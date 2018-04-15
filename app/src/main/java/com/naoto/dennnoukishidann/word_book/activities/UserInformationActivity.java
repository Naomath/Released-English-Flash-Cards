package com.naoto.dennnoukishidann.word_book.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.processings.BundleProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;
import com.naoto.dennnoukishidann.word_book.shareFragment.UserInformationFragment;

import java.util.List;

public class UserInformationActivity extends AppCompatActivity {

    List<String> user_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        gettingIntent();
        settingToolbar();
        settingFragment();
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

    public void gettingIntent() {
        user_information = IntentProcessing.inUserInformation(this);
    }

    public void settingFragment() {
        UserInformationFragment fragment = new UserInformationFragment();
        fragment.setArguments(BundleProcessing.toUserInformationFr(user_information.get(0), user_information.get(1)));
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
        IntentProcessing.backToMain(this, 2);
    }

    public void backActivityWithMessage(String message) {
        IntentProcessing.backToMainWithMessage(this, message, 2);
    }
}
