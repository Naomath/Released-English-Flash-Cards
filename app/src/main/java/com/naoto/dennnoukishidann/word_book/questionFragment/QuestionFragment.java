package com.naoto.dennnoukishidann.word_book.questionFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogFinishFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.interfaces.OnSendWordListener;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    OnSendWordListener onSendWordListener;
    OnQuestionListener onQuestionListener;
    Word word;
    View view;

    public QuestionFragment() {
        //empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_question, container, false);
        settingListener();
        settingTextView();
        return view;
    }


    public void gettingWord() {
        //出題するワードを出す
        word = onSendWordListener.onSendWord();
    }

    public void settingListener() {
        //リスナーの設定
        view.findViewById(R.id.button_next).setOnClickListener(this);
        onSendWordListener = (OnSendWordListener) getActivity();
        gettingWord();
        onQuestionListener = (OnQuestionListener) getActivity();
    }

    public void settingTextView() {
        //textviewの設定
        TextView text_question = (TextView) view.findViewById(R.id.text_question);
        String string_question = word.getOriginal();
        text_question.setText(string_question);
    }

    public void showedFinishFragment(){
        //back_keyが押された時にdialogを表示する時に使う
        CustomDialogFinishFragment customDialogFinishFragment = new CustomDialogFinishFragment();
        customDialogFinishFragment.show(getFragmentManager(),"finish");
    }


    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.button_next:
                    onQuestionListener.sendChange(0);
                    //ここでフラグメント変更を通知
                    break;
            }
        }
    }

    public interface OnQuestionListener {
        //fragmentを変更するのを教えるために使うローカルインターフェイス。AnswerFragmentも持っている
        void sendChange(int which);
    }
}

