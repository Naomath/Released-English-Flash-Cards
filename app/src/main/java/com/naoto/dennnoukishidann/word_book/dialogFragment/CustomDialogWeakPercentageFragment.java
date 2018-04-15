package com.naoto.dennnoukishidann.word_book.dialogFragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.NumberPicker;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;

public class CustomDialogWeakPercentageFragment extends DialogFragment {

    Dialog dialog;
    NumberPicker numberPicker;

    public CustomDialogWeakPercentageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        settingThisView();
        settingDecideButton();
        settingNumberPicker();
        return dialog;
    }

    public void settingThisView() {
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_custom_dialog_weak_percentage);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void settingDecideButton() {
        dialog.findViewById(R.id.button_decide).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float percentage = (float) numberPicker.getValue();
                CallSharedPreference.saveWeakPercentage(getActivity(), percentage);
                finishThis();
            }
        });
    }

    public void settingNumberPicker() {
        numberPicker = (NumberPicker) dialog.findViewById(R.id.number_picker);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(1);
        numberPicker.setValue((int) CallSharedPreference.callWeakPercentage(getActivity()));
    }

    public void finishThis() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}
