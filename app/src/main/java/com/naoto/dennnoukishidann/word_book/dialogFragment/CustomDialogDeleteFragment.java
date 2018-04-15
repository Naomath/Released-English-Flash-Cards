package com.naoto.dennnoukishidann.word_book.dialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.interfaces.OnDeleteListener;

public class CustomDialogDeleteFragment extends DialogFragment implements View.OnClickListener{

    Dialog dialog;
    OnDeleteListener deleteListener;

    public CustomDialogDeleteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_custom_dialog_delete);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        settingListener();
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()){
                case R.id.button_yes:
                    deleteListener.deleteBook();
                    break;
                case R.id.button_no:
                    dialog.dismiss();
                    break;
            }
        }
    }

    public void settingListener(){
        dialog.findViewById(R.id.button_yes).setOnClickListener(this);
        dialog.findViewById(R.id.button_no).setOnClickListener(this);
        deleteListener = (OnDeleteListener)getActivity();
    }
}
