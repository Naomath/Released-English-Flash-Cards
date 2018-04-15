package com.naoto.dennnoukishidann.word_book.questionFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogFinishFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.WeakWord;
import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.interfaces.OnSendWordListener;

public class AnswerFragment extends Fragment implements View.OnClickListener {

    View view;
    OnSendWordListener onSendWordListener;
    OnAnswerListener onAnswerListener;
    Word word;


    public AnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_answer, container, false);
        settingListener();
        settingTextView();
        return view;
    }

    public void gettingWord() {
        //wordを取得
        word = onSendWordListener.onSendWord();
    }

    public void settingListener() {
        view.findViewById(R.id.button_know).setOnClickListener(this);
        view.findViewById(R.id.button_dont).setOnClickListener(this);
        onSendWordListener = (OnSendWordListener) getActivity();
        gettingWord();
        //ここでwordを取得する
        onAnswerListener = (OnAnswerListener) getActivity();
    }

    public void settingTextView() {
        TextView text_answer = (TextView) view.findViewById(R.id.text_answer);
        String string_answer = word.getTranslated();
        text_answer.setText(string_answer);
    }

    public void settingWordProportion(int mistake) {
        //出題された後のwordの誤答率
        int number_mistake = word.getNumber_mistake();
        int number_question = word.getNumber_question();
        word.setNumber_mistake(number_mistake + mistake);
        word.setNumber_question(number_question + 1);
        word.calculateProportion();
        SharedPreferences weak_preference = getActivity().getSharedPreferences("weak_percentage"
                , Context.MODE_PRIVATE);
        float weak_percentage = weak_preference.getFloat("percentage", 30f);
        if (word.getProportion() >= weak_percentage) {
            //誤答率30%以上で間違えやすい問題になる
            if(word.getExist_weak()==0){
                word.setExist_weak(1);
                WeakWord weak_word = new WeakWord(word.getId());
                weak_word.save();
                word.setWeak_id(weak_word.getId());
                //ここで間違えやすい単語として登録を消す
            }
        } else if (word.getExist_weak() == 1) {
            word.setExist_weak(0);
            WeakWord weak_word = WeakWord.findById(WeakWord.class, word.getWeak_id());
            weak_word.delete();
        }
        if (mistake == 1) {
            //もし間違えたなら間違えたとactivityに伝える
            onAnswerListener.addMistakeNumber();
        }
        word.save();
        onAnswerListener.sendChange(1);
    }

    public void showedFinishFragment() {
        //back_keyが押された時にdialogを表示する時に使う
        CustomDialogFinishFragment customDialogFinishFragment = new CustomDialogFinishFragment();
        customDialogFinishFragment.show(getFragmentManager(), "finish");
    }


    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.button_know:
                    settingWordProportion(0);
                    break;
                case R.id.button_dont:
                    settingWordProportion(1);
                    break;

            }
        }
    }

    public interface OnAnswerListener {
        //fragmentを変更するのを教えるために使うmethod。QuestionFragmentも持っている
        void sendChange(int which);

        //activityに間違えた時に実行して間違えた回数を増やすmethod
        void addMistakeNumber();
    }

}
