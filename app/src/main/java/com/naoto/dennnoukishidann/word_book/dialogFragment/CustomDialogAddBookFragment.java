package com.naoto.dennnoukishidann.word_book.dialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.naoto.dennnoukishidann.word_book.activities.MainActivity;
import com.naoto.dennnoukishidann.word_book.R;

public class CustomDialogAddBookFragment extends DialogFragment implements View.OnClickListener {

    Dialog dialog;
    Button decide;
    RelativeLayout error;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_custom_dialog_add_book);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        decide = (Button) dialog.findViewById(R.id.decide_button);
        decide.setOnClickListener(this);
        decide.setEnabled(false);
        error = (RelativeLayout) dialog.findViewById(R.id.relative_error);
        watchEdit();
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.decide_button:
                    EditText editText = (EditText) dialog.findViewById(R.id.edit_title);
                    String title = editText.getText().toString();
                    OnReturnTitleListener onReturnTitleListener = (OnReturnTitleListener) getActivity();
                    onReturnTitleListener.returnTitle(title);
                    break;
            }
        }
    }

    public void watchEdit() {
        EditText title = (EditText) dialog.findViewById(R.id.edit_title);
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    enableButton();
                } else {
                    disableButton();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void enableButton() {
        //ボタンの有効化
        decide.setEnabled(true);
        error.setVisibility(View.INVISIBLE);
    }

    public void disableButton() {
        //ボタンの無効化
        decide.setEnabled(false);
        error.setVisibility(View.VISIBLE);
    }

    public interface OnReturnTitleListener {
        void returnTitle(String title);
    }
}
