package com.naoto.dennnoukishidann.word_book.shareFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.adapters.InformationAdapter;
import com.naoto.dennnoukishidann.word_book.classes.InformationText;
import com.naoto.dennnoukishidann.word_book.classes.TextsAndNumbers;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogInputMessageFragment;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogOneButtonFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.processings.BundleProcessing;
import com.naoto.dennnoukishidann.word_book.processings.FirebaseProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;

import java.util.ArrayList;
import java.util.List;

public class ULBookInformationFragment extends Fragment implements View.OnClickListener {

    View view;
    TextsAndNumbers data;
    InformationAdapter informationAdapter;
    CustomDialogOneButtonFragment checkDialog;
    CustomDialogInputMessageFragment editDialog;
    OnFinishListener onFinishListener;

    public ULBookInformationFragment() {
        // Required empty public constructor
    }

    public static ULBookInformationFragment newInstance(String param1, String param2) {
        ULBookInformationFragment fragment = new ULBookInformationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gettingBundle();
        settingThisView(inflater, container);
        settingListView();
        settingListener();
        addItems();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (resultCode != Activity.RESULT_OK) {
                        return;
                    }
                    //firebaseからデータを消去する時の処理
                    checkDialog.dismiss();
                    delete();
                    return;
                case 1:
                    if (resultCode != Activity.RESULT_OK) {
                        return;
                    }
                    //『説明』を書き直す時の処理
                    editDialog.dismiss();
                    updateMessage(data);
                    return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.button_delete:
                    showDeleteDialog();
                    break;
                case R.id.button_edit:
                    showMessageDialog();
                    break;
            }
        }
    }

    public void addItems() {
        //listviewにitemを入れるメソッド
        List<InformationText> information = new ArrayList<>();
        information.add(new InformationText("説明", data.getTexts().get(0), 18f));
        information.add(new InformationText("ダウンロード回数", String.valueOf(data.getNumbers().get(0)) + "回", 25f));
        informationAdapter.addAll(information);
    }

    public void delete() {
        //bookのアップロードをやめる
        long id = Long.parseLong(data.getTexts().get(1));
        Book book = Book.findById(Book.class, id);
        FirebaseProcessing.deleteBook(book.getBook_path());
        book.setDone_upload(0);
        book.save();
        onFinishListener.finishActivity("単語帳のアップロードを取り消しました。");
    }

    public void updateMessage(Intent intent) {
        long id = Long.parseLong(data.getTexts().get(1));
        Book book = Book.findById(Book.class, id);
        FirebaseProcessing.updateMessage(book.getBook_path(), IntentProcessing.fromInputMessageDialogInAnyFragment(intent));
        onFinishListener.finishActivity("単語帳の説明を変更しました。");
    }

    public void gettingBundle() {
        this.data = BundleProcessing.inULBookInformationFr(this);
    }

    public void settingThisView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_ulbook_information, container, false);
    }

    public void settingListView() {
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        informationAdapter = new InformationAdapter(getActivity(), R.layout.adapter_information_non_clickable);
        listView.setAdapter(informationAdapter);
    }

    public void settingListener() {
        view.findViewById(R.id.button_delete).setOnClickListener(this);
        view.findViewById(R.id.button_edit).setOnClickListener(this);
        onFinishListener = (OnFinishListener) getActivity();
    }

    public void showDeleteDialog() {
        //firebaseから取り消すのかテェックするダイアログを表示するメソッド
        checkDialog = new CustomDialogOneButtonFragment();
        checkDialog.setArguments(BundleProcessing.toOneBtnDialog("取り消し？", "アップロードを取り消しますか？", 0));
        checkDialog.setTargetFragment(this, Activity.RESULT_OK);
        checkDialog.show(getFragmentManager(), "delete");
    }

    public void showMessageDialog() {
        //説明を書き込むダイアログを表示するメソッド
        editDialog = new CustomDialogInputMessageFragment();
        Book item = new Book();
        item.setMessage(data.getTexts().get(0));
        editDialog.setArguments(BundleProcessing.toInputMessageDialog(item, "説明", "単語帳の説明を入力してください。"
                , "アップデート", 1));
        editDialog.setTargetFragment(this, Activity.RESULT_OK);
        editDialog.show(getFragmentManager(), "edit");
    }

    public interface OnFinishListener {
        void finishActivity(String message);
    }
}

