package com.naoto.dennnoukishidann.word_book.activities;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.extendSugar.WeakWord;
import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;
import com.naoto.dennnoukishidann.word_book.questionFragment.AnswerFragment;
import com.naoto.dennnoukishidann.word_book.questionFragment.FinishQuestionFragment;
import com.naoto.dennnoukishidann.word_book.questionFragment.QuestionFragment;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.processings.MakeDateString;
import com.naoto.dennnoukishidann.word_book.interfaces.OnFinishListener;
import com.naoto.dennnoukishidann.word_book.interfaces.OnSendWordListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity implements OnSendWordListener
        , QuestionFragment.OnQuestionListener
        , AnswerFragment.OnAnswerListener
        , FinishQuestionFragment.OnFinishQuestionListener
        , OnFinishListener {

    List<Word> presented_items;
    //出す単語たちを全て入れるリスト
    Fragment fragment;
    int number_turn;
    //presented_itemsの何番目か
    //初期値は0
    int number_mistake;
    //間違えた回数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        number_turn = 0;
        //初期値を設定
        setContentView(R.layout.activity_question);
        gettingIntent();
        judgeExistWords();
        changeFragment(1);
        settingFragment();
        //流れ的には　        //gettingIntent()-->settingPresentedToday() or settingPresentedWeak()
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタンの処理
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (fragment != null && fragment instanceof QuestionFragment) {
                ((QuestionFragment) fragment).showedFinishFragment();
            } else if (fragment != null && fragment instanceof AnswerFragment) {
                ((AnswerFragment) fragment).showedFinishFragment();
            } else if (fragment != null && fragment instanceof FinishQuestionFragment) {
                intentToMain();
                //ここだけ特別
            }
            return true;
        } else {
            return false;
        }
    }

    public void judgeExistWords(){
        //ここでアイテムがないことが発覚したら終わらせる
        if (presented_items.size()==0){
            IntentProcessing.backToMainWithMessage(this,"この問題に該当する単語がまだありません。", 0);
        }
    }


    public void gettingIntent() {
        Intent intent = getIntent();
        int which_course = intent.getIntExtra("which_course", 0);
        switch (which_course) {
            case 0:
                settingPresentedItemsToday();
                break;
            case 1:
                settingPresentedItemsWeak();
                break;
            case 2:
                settingPresentedItemsBook(intent);

        }
    }

    public List<Book> gettingBooks(String date_string) {
        //今日の問題の単語たちを取得するときに使う
        List<Book> books = Book.listAll(Book.class);
        List<Book> returned_books = new ArrayList<>();
        for (Book item : books) {
            if (item.getDate().equals(date_string)) {
                //もし日にちが同じものをここで選定している
                returned_books.add(item);
            }
        }
        return returned_books;
    }

    public void settingPresentedItemsToday() {
        //今日の問題のセッティングをやる
        presented_items = new ArrayList<>();
        List<String> string_dates = new ArrayList<>();
        string_dates.add(MakeDateString.makeDateNow());
        string_dates.add(MakeDateString.makeDateYesterday());
        string_dates.add(MakeDateString.makeDateOneWeekBefore());
        string_dates.add(MakeDateString.makeDateOneMonthBefore());
        for (String item : string_dates) {
            //その日にちの単語帳を取得してそれから単語隊を取得してそっからwordsというlistに入れる
            List<Book> booksWord = gettingBooks(item);
            for (Book item2: booksWord) {
                List<Word> items = item2.returnWords();
                for (Word item3 : items) {
                    presented_items.add(item3);
                }
            }
        }
    }


    public void settingPresentedItemsWeak() {
        //苦手な問題のセッティングをやる
        presented_items = new ArrayList<>();
        List<WeakWord> weak_items = WeakWord.listAll(WeakWord.class);
        for (WeakWord weak_item : weak_items) {
            Word word_item = Word.findById(Word.class, weak_item.getWord_id());
            presented_items.add(word_item);
        }
    }

    public void settingPresentedItemsBook(Intent intent) {
        //単語帳自体で問題を出す時
        long book_id = intent.getLongExtra("book_id", 0l);
        Book book = Book.findById(Book.class, book_id);
        List<Word> resource = book.returnWords();
        presented_items = new ArrayList<>(resource);
        //上のlistがディープコピーする先のlist
        //こういう破壊的なコンストラクタがあったとは。。
    }

    public void settingFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.relative, fragment);
        transaction.commit();
    }

    public void changeFragment(int which) {
        switch (which) {
            case 0:
                fragment = new AnswerFragment();
                break;
            case 1:
                fragment = new QuestionFragment();
                break;
            case 2:
                fragment = new FinishQuestionFragment();
                break;
        }
    }

    @Override
    public void sendChange(int which) {
        if (which == 1) {
            number_turn++;
            //一周した場合ということ
        }
        if (presented_items.size() == number_turn && which == 1) {
            //終わる処理をかく
            changeFragment(2);
            settingFragment();
        } else {
            //通常の処理をかく
            changeFragment(which);
            settingFragment();
        }
    }

    @Override
    public Word onSendWord() {
        Word sended = presented_items.get(number_turn);
        return sended;
    }

    @Override
    public int sendAllNumber() {
        return presented_items.size();
    }

    @Override
    public int sendMistakeNumber() {
        return presented_items.size() - number_mistake;
    }

    @Override
    public void intentToMain() {
        if (CallSharedPreference.callTutorialMainStep(this) != 2) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            CallSharedPreference.addTutorialMainStep(this);
            IntentProcessing.backToTutorialMain(this);
        }
    }

    @Override
    public void addMistakeNumber() {
        number_mistake++;
    }

    @Override
    public void sendFinish(boolean which) {
        intentToMain();
    }
}
