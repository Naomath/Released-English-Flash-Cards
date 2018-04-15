package com.naoto.dennnoukishidann.word_book.activities;


import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.naoto.dennnoukishidann.word_book.wordsFragments.AddWordsFragment;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.interfaces.OnFinishListener;
import com.naoto.dennnoukishidann.word_book.interfaces.OnInputListener;

public class AddWordActivity extends AppCompatActivity implements OnFinishListener, OnInputListener {

    AddWordsFragment fragment;
    Toolbar toolbar;
    String title;

    @Override
    public void sendFinish(boolean which) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("which_fragment", 1);
        intent.putExtra("return", true);
        intent.putExtra("please_toast", which);
        intent.putExtra("message_toast","単語帳を登録しました");
        startActivity(intent);
    }

    @Override
    public String sendText() {
        return title;
    }

    @Override
    public Long sendLong() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        Intent intent = getIntent();
        //title取得
        title = intent.getStringExtra("title");
        settingToolbar();
        settingFragment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタンの処理
            finishActivity();
            return true;
        } else {
            return false;
        }
    }

    public void finishActivity() {
        //戻るに関する全ての処理
        fragment.showDialog();
    }

    public void settingFragment() {
        fragment = new AddWordsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.relative_background, fragment);
        transaction.commit();
    }

    public void settingToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });
        setTitle(title);
    }

    public void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
