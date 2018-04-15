package com.naoto.dennnoukishidann.word_book.processings;

import android.os.Bundle;

import com.naoto.dennnoukishidann.word_book.classes.TextsAndNumbers;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogInputMessageFragment;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogOneButtonFragment;
import com.naoto.dennnoukishidann.word_book.downloadFragment.DLBookInformationFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.shareFragment.ULBookInformationFragment;
import com.naoto.dennnoukishidann.word_book.shareFragment.UserInformationFragment;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gotounaoto on 2018/03/18.
 */

public class BundleProcessing {

    public static Bundle toDlBookInformationFrInDownload(String book_path) {
        //DownloadActivityでDlBookInformationFragmentを生成する時に渡すbundle
        Bundle bundle = new Bundle();
        bundle.putString("book_path", book_path);
        return bundle;
    }

    public static String inDlBookInformationFrInDownload(DLBookInformationFragment fragment) {
        //DlBookInformationFrで渡されて来たbundleを取得する
        Bundle bundle = fragment.getArguments();
        return bundle.getString("book_path");
    }

    public static Bundle toInputMessageDialog(Book item, String title, String edit_hint, String btn_message, int requestCode) {
        //MessageDialogにWordsFragmentから
        Bundle bundle = new Bundle();
        bundle.putString("message", item.getMessage());
        bundle.putString("title", title);
        bundle.putString("edit_hint", edit_hint);
        bundle.putString("btn_message", btn_message);
        bundle.putInt("requestCode", requestCode);
        return bundle;
    }

    public static TextsAndNumbers inInputMessageDialog(CustomDialogInputMessageFragment fragment) {
        //MessageDialogに送られてきたのを取得する
        Bundle bundle = fragment.getArguments();
        List<String> texts = Arrays.asList(bundle.getString("message"), bundle.getString("title"), bundle.getString("edit_hint")
                , bundle.getString("btn_message"));
        List<Integer> numbers = Arrays.asList(bundle.getInt("requestCode"));
        TextsAndNumbers item = new TextsAndNumbers(texts, numbers);
        return item;
    }

    public static Bundle toOneBtnDialog(String title, String message, int requestCode) {
        //OneBtnDialogを作るときに渡すbundle
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        bundle.putInt("requestCode", requestCode);
        return bundle;
    }

    public static TextsAndNumbers inOneBtnDialog(CustomDialogOneButtonFragment fragment) {
        //OneBtnDilogで送られてきたbundleを取得する
        Bundle bundle = fragment.getArguments();
        List<String> texts = Arrays.asList(bundle.getString("title"), bundle.getString("message"));
        List<Integer> numbers = Arrays.asList(bundle.getInt("requestCode"));
        TextsAndNumbers item = new TextsAndNumbers(texts, numbers);
        return item;
    }

    public static Bundle toULBookInformationFr(TextsAndNumbers data) {
        //ULBookInformationFragmentに対するbundle
        Bundle bundle = new Bundle();
        bundle.putString("message", data.getTexts().get(1));
        //上の要素が1になっているところに注意!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        bundle.putString("id", data.getTexts().get(2));
        bundle.putInt("download_time", data.getNumbers().get(0));
        return bundle;
    }

    public static TextsAndNumbers inULBookInformationFr(ULBookInformationFragment fragment) {
        Bundle bundle = fragment.getArguments();
        List<String> texts = Arrays.asList(bundle.getString("message"), bundle.getString("id"));
        List<Integer> numbers = Arrays.asList(bundle.getInt("download_time"));
        TextsAndNumbers data = new TextsAndNumbers(texts, numbers);
        return data;
    }

    public static Bundle toUserInformationFr(String user_name, String user_path) {
        Bundle bundle = new Bundle();
        bundle.putString("name", user_name);
        bundle.putString("path", user_path);
        return bundle;
    }

    public static List<String> inUserInformationFr(UserInformationFragment fragment) {
        Bundle bundle = fragment.getArguments();
        List<String> items = Arrays.asList(bundle.getString("name"), bundle.getString("path"));
        return items;
    }

}
