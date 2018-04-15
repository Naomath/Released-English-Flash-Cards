package com.naoto.dennnoukishidann.word_book.dialogFragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.naoto.dennnoukishidann.word_book.activities.QuestionActivity;
import com.naoto.dennnoukishidann.word_book.R;

public class CustomDialogCheckSolveFragment extends DialogFragment implements View.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_custom_dialog_check_solve);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.findViewById(R.id.today_button).setOnClickListener(this);
        dialog.findViewById(R.id.weak_button).setOnClickListener(this);
        return dialog;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.today_button:
                    Intent intent_today = new Intent(getActivity(), QuestionActivity.class);
                    intent_today.putExtra("which_course", 0);
                    startActivity(intent_today);
                    dismiss();
                    break;
                case R.id.weak_button:
                    Intent intent_weak = new Intent(getActivity(), QuestionActivity.class);
                    intent_weak.putExtra("which_course", 1);
                    startActivity(intent_weak);
                    dismiss();
                    break;
            }
        }
    }
}
