package com.naoto.dennnoukishidann.word_book.dialogFragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.naoto.dennnoukishidann.word_book.R;

public class CustomDialogSortFragment extends DialogFragment implements View.OnClickListener {

    Dialog dialog;

    public static CustomDialogSortFragment newInstance(Fragment target, int requestCode) {
        CustomDialogSortFragment fragment = new CustomDialogSortFragment();
        fragment.setTargetFragment(target, requestCode);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    public CustomDialogSortFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        settingDialog();
        settingListener();
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()){
                case R.id.button_faster:
                    submitToTargetFragment(0);
                    break;
                case R.id.button_older:
                    submitToTargetFragment(1);
                    break;
            }
        }
    }

    public void settingDialog() {
        //ダイアログの詳細設定
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_custom_dialog_sort);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void settingListener() {
        //リスナーの設定
        dialog.findViewById(R.id.button_faster).setOnClickListener(this);
        dialog.findViewById(R.id.button_older).setOnClickListener(this);
    }

    public void submitToTargetFragment(int which){
        Fragment target = getTargetFragment();
        Intent intent = new Intent();
        target.onActivityResult(Activity.RESULT_OK,which, intent);
    }
}
