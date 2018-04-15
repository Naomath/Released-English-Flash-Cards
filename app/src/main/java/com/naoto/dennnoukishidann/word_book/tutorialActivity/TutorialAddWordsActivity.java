package com.naoto.dennnoukishidann.word_book.tutorialActivity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;
import com.naoto.dennnoukishidann.word_book.tutorialFragment.TutorialAddWordsFragment;
import com.github.amlcurran.showcaseview.ShowcaseView;

public class TutorialAddWordsActivity extends AppCompatActivity implements TutorialAddWordsFragment.OnFinishListener
        , TutorialAddWordsFragment.OnSendTextListener {

    Toolbar toolbar;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_add_words);
        gettingIntent();
        settingToolbar();
        settingFragment();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
        if(event.getAction() == KeyEvent.ACTION_UP){
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    //ダイアログ表示などの処理を行う時はここに記述する
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void finishActivity(String message) {
        IntentProcessing.backToTutorialMainWithMessage(this, message, 0);
    }

    @Override
    public String sendText(){
        return title;
    }

    public void gettingIntent() {
        title = IntentProcessing.inTutorialAddWords(this);
    }

    public void settingToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_white_24dp);
        setTitle(title);
    }

    public void settingFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.relative_background, TutorialAddWordsFragment.newInstance());
        transaction.commit();
    }
}
