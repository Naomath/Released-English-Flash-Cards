package com.naoto.dennnoukishidann.word_book.tutorialFragment;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogCheckSolveFragment;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class TutorialHomeFragment extends Fragment {

    View view;
    int number_step;
    ShowcaseView showcaseView;

    public TutorialHomeFragment() {
        // Required empty public constructor
    }

    public static TutorialHomeFragment newInstance() {
        TutorialHomeFragment fragment = new TutorialHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gettingTutorialStep();
        settingThisView(inflater, container);
        settingShowcaseView();
        settingButton();
        return view;
    }

    public void gettingTutorialStep() {
        number_step = CallSharedPreference.callTutorialMainStep(getActivity());
    }

    public void settingThisView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_tutorial_home, container, false);
    }

    public void settingButton(){
        ImageButton button = (ImageButton)view.findViewById(R.id.check_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogCheckSolveFragment dialogCheckFragment = new CustomDialogCheckSolveFragment();
                dialogCheckFragment.show(getFragmentManager(), "check");
            }
        });
    }

    public void settingShowcaseView() {
        if (number_step == 2) {
            showcaseView = new ShowcaseView.Builder(getActivity())
                    .setTarget(new ViewTarget(view.findViewById(R.id.check_button)))
                    .setContentTitle("問題を解く")
                    .setContentText("では問題を解いていきましょう。まずボタンを押して、『今日の』を選択してください。ちなみに今日の問題はその日、昨日、一週間前、一ヶ月前に登録した単語が出題されます。")
                    .setStyle(R.style.CustomShowcaseTheme)
                    .withMaterialShowcase()
                    .doNotBlockTouches() //ShowcaseView下のボタンを触れるように。
                    .build();
            showcaseView.hideButton();
        }
    }

}
