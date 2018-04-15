package com.naoto.dennnoukishidann.word_book.processings;

import android.app.Activity;
import android.content.Intent;

import com.naoto.dennnoukishidann.word_book.activities.DLBookInformationActivity;
import com.naoto.dennnoukishidann.word_book.activities.MainActivity;
import com.naoto.dennnoukishidann.word_book.activities.ULBookInformationActivity;
import com.naoto.dennnoukishidann.word_book.activities.UserInformationActivity;
import com.naoto.dennnoukishidann.word_book.classes.TextsAndNumbers;
import com.naoto.dennnoukishidann.word_book.classes.User;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.tutorialActivity.TutorialAddWordsActivity;
import com.naoto.dennnoukishidann.word_book.tutorialActivity.TutorialMainActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gotounaoto on 2018/03/18.
 */

public class IntentProcessing {

    public static void backToMain(Activity activity, int which_fragment) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("which_fragment", which_fragment);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void backToMainWithMessage(Activity activity, String message, int which_fragment) {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("which_fragment", which_fragment);
        intent.putExtra("please_toast", true);
        intent.putExtra("message", message);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void backToTutorialMain(Activity activity) {
        Intent intent = new Intent(activity, TutorialMainActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void backToTutorialMainWithMessage(Activity activity, String message, int which_fragment) {
        Intent intent = new Intent(activity, TutorialMainActivity.class);
        intent.putExtra("which_fragment", which_fragment);
        intent.putExtra("please_toast", true);
        intent.putExtra("message", message);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void ToDLBookInformation(Activity activity, String book_path, int original, User user) {
        //MainActivityからDownloadActivityに遷移する
        //originalが0だとmainから、1だとuserInformation
        Intent intent = new Intent(activity, DLBookInformationActivity.class);
        intent.putExtra("book_path", book_path);
        intent.putExtra("original", original);
        if (user != null) {
            intent.putExtra("name", user.getName());
            intent.putExtra("path", user.getUser_path());
        }
        activity.startActivity(intent);
        activity.finish();
    }

    public static TextsAndNumbers inDLBookInformation(Activity activity) {
        //MainActivityからDownloadActivityに送られて来たものをゲットしてリターンする
        Intent intent = activity.getIntent();
        List<String> texts = new ArrayList<>();
        texts.add(intent.getStringExtra("book_path"));
        if (intent.getStringExtra("name") != null) {
            texts.add(intent.getStringExtra("name"));
            texts.add(intent.getStringExtra("path"));
        }
        List<Integer> numbers = Arrays.asList(intent.getIntExtra("original", 0));
        TextsAndNumbers textsAndNumbers = new TextsAndNumbers(texts, numbers);
        return textsAndNumbers;
    }

    public static Intent fromInputMessageDialog(String s) {
        //MessageDialogから呼び出し元のところまでreturnする
        Intent intent = new Intent();
        intent.putExtra("message", s);
        return intent;
    }

    public static String fromInputMessageDialogInAnyFragment(Intent data) {
        //MessageDialogから送られてきたもの
        return data.getStringExtra("message");
    }

    public static void fromAboutMeToUlInformation(Activity activity, Book item) {
        //ABoutMeFragmentからULBookInformationに遷移する時の処理
        Intent intent = new Intent(activity, ULBookInformationActivity.class);
        intent.putExtra("message", item.getMessage());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("download_time", item.getDownload_time());
        intent.putExtra("id", String.valueOf(item.getId()));
        activity.startActivity(intent);
        activity.finish();
    }

    public static TextsAndNumbers fromAboutMeInUlInformation(Activity activity) {
        //ULBookInformationでAboutMeFragmentから送られてきた時の処理
        Intent intent = activity.getIntent();
        List<String> texts = Arrays.asList(intent.getStringExtra("title"), intent.getStringExtra("message")
                , intent.getStringExtra("id"));
        List<Integer> numbers = Arrays.asList(intent.getIntExtra("download_time", 0));
        TextsAndNumbers item = new TextsAndNumbers(texts, numbers);
        return item;
    }

    public static void toTutorialAddWords(Activity activity, String title) {
        Intent intent = new Intent(activity, TutorialAddWordsActivity.class);
        intent.putExtra("title", title);
        activity.startActivity(intent);
        activity.finish();
    }

    public static String inTutorialAddWords(Activity activity) {
        Intent intent = activity.getIntent();
        return intent.getStringExtra("title");
    }

    public static void toUserInformation(Activity activity, User user) {
        Intent intent = new Intent(activity, UserInformationActivity.class);
        intent.putExtra("name", user.getName());
        intent.putExtra("path", user.getUser_path());
        activity.startActivity(intent);
        activity.finish();
    }

    public static List<String> inUserInformation(Activity activity) {
        Intent intent = activity.getIntent();
        List<String> items = Arrays.asList(intent.getStringExtra("name"), intent.getStringExtra("path"));
        return items;
    }

}
