package com.naoto.dennnoukishidann.word_book.mainFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.processings.BundleProcessing;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogInputMessageFragment;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogWeakPercentageFragment;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.adapters.InformationAdapter;
import com.naoto.dennnoukishidann.word_book.classes.InformationText;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;

import java.util.ArrayList;
import java.util.List;

public class SettingsUserFragment extends Fragment {

    View view;
    ListView listView;
    InformationAdapter adapter;
    CustomDialogInputMessageFragment dialogInputMessageFragment;

    public SettingsUserFragment() {
        // Required empty public constructor
    }

    public static SettingsUserFragment newInstance() {
        SettingsUserFragment fragment = new SettingsUserFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    //これはユーザー名が変更された時の処理
                    updateUserName(data);
                    return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_setting_user, container, false);
        settingListView();
        addItemList();
        return view;
    }

    public void settingListView() {
        //listviewの設定
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new InformationAdapter(getActivity(), R.layout.adapter_information_clickable);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InformationText item = (InformationText) adapter.getItem(i);
                int item_id = item.getId();
                switch (item_id) {
                    case 0:
                        case0();
                        break;
                    case 1:
                        case1();
                        break;
                }
            }
        });
        listView.setAdapter(adapter);
    }

    public void case0() {
        //listviewでcase0の時の処理
        //ここでユーザー名を変える
        dialogInputMessageFragment = new CustomDialogInputMessageFragment();
        Book item = new Book();
        item.setMessage(CallSharedPreference.callUserName(getActivity()));
        dialogInputMessageFragment.setArguments(BundleProcessing.toInputMessageDialog(item, "ユーザー名", "ユーザー名を入力してください。"
                , "変更", 0));
        dialogInputMessageFragment.setTargetFragment(this, 0);
        dialogInputMessageFragment.show(getFragmentManager(), "user_name");
        //この上のtagでどれにするか判別する
    }

    public void case1() {
        //間違えやすい問題のパーセンテージを変える
        CustomDialogWeakPercentageFragment dialogWeakPercentageFragment = new CustomDialogWeakPercentageFragment();
        dialogWeakPercentageFragment.show(getFragmentManager(), "weak_percentage");
    }

    public void addItemList() {
        //listviewに要素を追加する
        List<InformationText> list_item = new ArrayList<>();
        InformationText item1 = new InformationText("ユーザー名", CallSharedPreference.callUserName(getActivity()), 0, 25f);
        list_item.add(item1);
        InformationText item2 = new InformationText("間違えやすい問題になる誤答率"
                , String.valueOf(CallSharedPreference.callWeakPercentage(getActivity())) + "%", 1, 25f);
        list_item.add(item2);
        adapter.add(item1);
        adapter.add(item2);
    }

    public void updateUserName(Intent data){
        String user_name = IntentProcessing.fromInputMessageDialogInAnyFragment(data);
        CallSharedPreference.saveUserName(getActivity(), user_name);
    }

}
