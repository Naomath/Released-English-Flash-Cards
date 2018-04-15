package com.naoto.dennnoukishidann.word_book.wordsFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.naoto.dennnoukishidann.word_book.activities.AddWordActivity;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogFinishFragment;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogWordAddFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.adapters.WordsAdapter;
import com.naoto.dennnoukishidann.word_book.processings.MakeDateString;
import com.naoto.dennnoukishidann.word_book.interfaces.OnFinishListener;
import com.naoto.dennnoukishidann.word_book.interfaces.OnInputListener;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class AddWordsFragment extends Fragment implements View.OnClickListener {

    View view;
    SwipeMenuListView listView;
    WordsAdapter adapter;
    CustomDialogWordAddFragment dialog;
    AddWordActivity source;
    OnInputListener onInputListener;
    OnFinishListener onFinishListener;
    String title;
    Button decide_button;
    RelativeLayout error_layout;
    int number_items;

    public AddWordsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        source = (AddWordActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_words, container, false);
        gettingViews();
        settingListener();
        settingListView();
        settingSwipeMenu();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                String original = data.getStringExtra("original");
                String translated = data.getStringExtra("translated");
                String part = data.getStringExtra("part");
                Word word = new Word(original, translated, part);
                adapter.add(word);
                number_items++;
                enableButton();
                //アイテム数をプラス1しとく
                dialog.dismiss();
                return;
            case 2:
                if (resultCode != Activity.RESULT_OK) {
                    return;
                }
                source.goHome();
                //activityに遷移させる
                //ダイアログからきてこのfragmentを中継してactivityへ!!!
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.button_add:
                    toAdd();
                    break;
                case R.id.button_decide:
                    toDecide();
                    break;
            }
        }
    }

    public  void toAdd(){
        //addボタンが押された時の処理
        dialog = new CustomDialogWordAddFragment();
        dialog.setTargetFragment(this, 1);
        dialog.show(getFragmentManager(), "add_words");
    }

    public void toDecide(){
        //decideボタンが押された時の処理
        List<Word> wordList = new ArrayList<>();
        for (int i = 0, length = adapter.getCount(); i < length; i++) {
            Word item = adapter.getItem(i);
            wordList.add(item);
        }
        SugarRecord.saveInTx(wordList);
        Word firstWord = wordList.get(0);
        Word lastWord = wordList.get(adapter.getCount() - 1);
        Book group = new Book(title, firstWord.getId(), lastWord.getId(),MakeDateString.makeDateNow());
        group.setUser_name(CallSharedPreference.callUserName(getActivity()));
        group.setDone_upload(0);
        group.save();
        onFinishListener.sendFinish(true);
    }

    public void gettingViews() {
        //ボタンのどのviewを取得
        decide_button = (Button) view.findViewById(R.id.button_decide);
        error_layout = (RelativeLayout) view.findViewById(R.id.relative_error);
    }

    public void settingListener() {
        view.findViewById(R.id.button_add).setOnClickListener(this);
        view.findViewById(R.id.button_decide).setOnClickListener(this);
        onInputListener = (OnInputListener) getActivity();
        onFinishListener = (OnFinishListener) getActivity();
        //ここでタイトル取得も
        title = onInputListener.sendText();
    }

    public void settingListView() {
        listView = (SwipeMenuListView) view.findViewById(R.id.list_view);
        adapter = new WordsAdapter(getActivity(), R.layout.adapter_words);
        listView.setAdapter(adapter);
        disableButton();
        //最初は無効化しとく
        number_items = 0;
        //アイテムの数をゼロに最初はしとく
    }

    public void settingSwipeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            //横に出るやつ
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xd3, 0x2f, 0x2f)));
                deleteItem.setIcon(R.drawable.ic_delete_white_48dp);
                deleteItem.setWidth(300);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Word item = (Word) adapter.getItem(position);
                        adapter.remove(item);
                        number_items--;
                        if (number_items == 0) {
                            disableButton();
                        }
                        break;
                }
                return false;
            }
        });
    }

    public void showDialog() {
        CustomDialogFinishFragment fragment = new CustomDialogFinishFragment();
        fragment.show(getFragmentManager(), "finish");
    }

    public void enableButton() {
        //ボタンの有効化
        decide_button.setEnabled(true);
        error_layout.setVisibility(View.INVISIBLE);
    }

    public void disableButton() {
        //ボタンの無効化
        decide_button.setEnabled(false);
        error_layout.setVisibility(View.VISIBLE);
    }


}
