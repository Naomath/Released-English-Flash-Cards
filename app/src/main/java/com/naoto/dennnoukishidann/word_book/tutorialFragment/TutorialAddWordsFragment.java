package com.naoto.dennnoukishidann.word_book.tutorialFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.adapters.WordsAdapter;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogWordAddFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;
import com.naoto.dennnoukishidann.word_book.processings.MakeDateString;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

public class TutorialAddWordsFragment extends Fragment implements View.OnClickListener {

    View view;
    SwipeMenuListView listView;
    WordsAdapter adapter;
    int number_items;
    CustomDialogWordAddFragment dialogWordAddFragment;
    OnFinishListener onFinishListener;
    OnSendTextListener onSendTextListener;
    ShowcaseView showcaseView1;
    ShowcaseView showcaseView2;

    public TutorialAddWordsFragment() {
        // Required empty public constructor
    }

    public static TutorialAddWordsFragment newInstance() {
        TutorialAddWordsFragment fragment = new TutorialAddWordsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                dialogWordAddFragment.dismiss();
                showcaseView1.hide();
                settingShowcaseView2();
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settingThisView(inflater, container);
        settingListener();
        settingListView();
        settingSwipeMenu();
        settingShowcaseView1();
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.button_add:
                    showAddWordDialog();
                    break;
                case R.id.button_decide:
                    decide();
                    break;
            }
        }
    }

    public void showAddWordDialog() {
        dialogWordAddFragment = new CustomDialogWordAddFragment();
        dialogWordAddFragment.setTargetFragment(this, 1);
        dialogWordAddFragment.show(getFragmentManager(), "add_words");
    }

    public void decide() {
        List<Word> wordList = new ArrayList<>();
        for (int i = 0, length = adapter.getCount(); i < length; i++) {
            Word item = adapter.getItem(i);
            wordList.add(item);
        }
        SugarRecord.saveInTx(wordList);
        Word firstWord = wordList.get(0);
        Word lastWord = wordList.get(adapter.getCount() - 1);
        Book group = new Book(onSendTextListener.sendText(), firstWord.getId(), lastWord.getId(), MakeDateString.makeDateNow());
        group.setUser_name(CallSharedPreference.callUserName(getActivity()));
        group.setDone_upload(0);
        group.save();
        showcaseView2.hide();
        CallSharedPreference.addTutorialMainStep(getActivity());
        onFinishListener.finishActivity("登録しました。");
    }

    public void enableButton() {
        //ボタンの有効化
        view.findViewById(R.id.button_decide).setEnabled(true);
        view.findViewById(R.id.relative_error).setVisibility(View.INVISIBLE);
    }

    public void disableButton() {
        //ボタンの無効化
        view.findViewById(R.id.button_decide).setEnabled(false);
        view.findViewById(R.id.relative_error).setVisibility(View.VISIBLE);
    }

    public void settingThisView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_tutorial_add_words, container, false);
    }

    public void settingListener() {
        view.findViewById(R.id.button_add).setOnClickListener(this);
        view.findViewById(R.id.button_decide).setOnClickListener(this);
        onFinishListener = (OnFinishListener) getActivity();
        onSendTextListener = (OnSendTextListener) getActivity();
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

    public void settingShowcaseView1() {
        showcaseView1 = new ShowcaseView.Builder(getActivity())
                .setTarget(new ViewTarget(view.findViewById(R.id.button_add)))
                .setContentTitle("単語帳の登録")
                .setContentText("Addボタンを押して単語帳を登録してください。")
                .withMaterialShowcase()
                .setStyle(R.style.CustomShowcaseTheme)
                .doNotBlockTouches() //ShowcaseView下のボタンを触れるように。
                .build();
        showcaseView1.hideButton();
    }

    public void settingShowcaseView2() {
        showcaseView2 = new ShowcaseView.Builder(getActivity())
                .setTarget(new ViewTarget(view.findViewById(R.id.button_decide)))
                .setContentTitle("単語帳の登録")
                .setContentText("DECIDEボタンを押して単語帳の登録を完了してください。")
                .setStyle(R.style.CustomShowcaseTheme)
                .withMaterialShowcase()
                .doNotBlockTouches() //ShowcaseView下のボタンを触れるように。
                .build();
        showcaseView2.hideButton();
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

    public interface OnFinishListener {
        void finishActivity(String message);
    }

    public interface OnSendTextListener {
        String sendText();
    }
}
