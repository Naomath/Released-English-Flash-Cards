package com.naoto.dennnoukishidann.word_book.dialogFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.classes.TextsAndNumbers;
import com.naoto.dennnoukishidann.word_book.processings.BundleProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;

import java.util.List;

public class CustomDialogOneButtonFragment extends DialogFragment implements View.OnClickListener {

    Dialog dialog;
    int requestCode;
    TextsAndNumbers data;

    public CustomDialogOneButtonFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gettingBundle();
        settingDialog();
        settingButton();
        settingTextView();
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if(view != null){
            switch (view.getId()){
                case R.id.decide_button:
                    decide();
                    break;
            }
        }
    }

    public void decide(){
        //buttonが押された時の処理
        Fragment target = getTargetFragment();
        target.onActivityResult(requestCode, Activity.RESULT_OK, null);
    }

    public void gettingBundle(){
        data = BundleProcessing.inOneBtnDialog(this);
    }

    public void settingDialog() {
        //ダイアログの詳細設定
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_custom_dialog_one_button);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void settingButton(){
        //buttonの設定
        Button button = (Button)dialog.findViewById(R.id.decide_button);
        button.setOnClickListener(this);
    }

    public void settingTextView(){
        //textViewの設定
        TextView title = (TextView)dialog.findViewById(R.id.title);
        title.setText(data.getTexts().get(0));
        TextView message = (TextView)dialog.findViewById(R.id.message);
        message.setText(data.getTexts().get(1));
        //ここから下で呼び出し元のfragmentに返すrequestCodeの設定を行う
        this.requestCode = data.getNumbers().get(0);
    }
}
