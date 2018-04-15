package com.naoto.dennnoukishidann.word_book.questionFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.processings.MakeString;

import java.util.ArrayList;
import java.util.List;

public class FinishQuestionFragment extends Fragment implements View.OnClickListener {

    View view;
    OnFinishQuestionListener onFinishQuestionListener;
    int number_all;
    //問題数
    int number_mistake;
    //間違えた回数

    public FinishQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_finish_question, container, false);
        settingListener();
        settingTextView();
        return view;
    }

    public void gettingNumbers() {
        //問題数と間違えた回数を取得する
        number_all =onFinishQuestionListener.sendAllNumber();
        number_mistake = onFinishQuestionListener.sendMistakeNumber();
    }

    public List<String> gettingShowedText() {
        //textviewに表示するtextを取得してreturnする
        //一つ目と二つ目に限る
        List<String> texts = new ArrayList<>();
        SharedPreferences user_preference = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_name = user_preference.getString("name", null);
        List<String> first_items = new ArrayList<>();
        first_items.add(user_name);
        first_items.add("さんは");
        first_items.add(String.valueOf(number_all));
        first_items.add("問中");
        texts.add(MakeString.makeString(first_items));
        List<String> second_items = new ArrayList<>();
        second_items.add(String.valueOf(number_mistake));
        second_items.add("問");
        texts.add(MakeString.makeString(second_items));
        return texts;
    }

    public void settingListener() {
        view.findViewById(R.id.button_go_home).setOnClickListener(this);
        onFinishQuestionListener = (OnFinishQuestionListener) getActivity();
        gettingNumbers();
    }

    public void settingTextView() {
        //textviewの設定をする
        TextView text_first = (TextView) view.findViewById(R.id.text_first);
        TextView text_second = (TextView) view.findViewById(R.id.text_second);
        TextView text_third = (TextView) view.findViewById(R.id.text_third);
        List<String> showed = gettingShowedText();
        text_first.setText(showed.get(0));
        text_second.setText(showed.get(1));
        text_third.setText(R.string.textview_message3_finish);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.button_go_home:
                    onFinishQuestionListener.intentToMain();
                    break;
            }
        }
    }


    public interface OnFinishQuestionListener{
        //問題数を送るmethod
        int sendAllNumber();
        //間違えた数を送るmethod
        int sendMistakeNumber();
        //MainActivityに遷移するmethod
        void intentToMain();
    }

}
