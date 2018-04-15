package com.naoto.dennnoukishidann.word_book.processings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gotounaoto on 2018/03/16.
 */

public class CallSharedPreference {
    //SharedPreferenceについての処理が書いてある

    public static SharedPreferences callUserPreference(Context context) {
        //userというプリファレンスを呼び出して返す
        SharedPreferences user_preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return user_preferences;
    }

    public static SharedPreferences callTutorialPreference(Context context) {
        //チュートリアルに関するプリファレンス
        SharedPreferences preferences = context.getSharedPreferences("tutorial", Context.MODE_PRIVATE);
        return preferences;
    }

    public static String callUserId(Context context) {
        //user_idを返すメソッド
        String user_id = callUserPreference(context).getString("id", null);
        return user_id;
    }

    public static String callUserName(Context context) {
        //user_nameを返すメソッド
        String user_name = callUserPreference(context).getString("name", null);
        return user_name;
    }

    public static void saveUserName(Context context, String name) {
        SharedPreferences.Editor editor = callUserPreference(context).edit();
        editor.putString("name", name);
        editor.commit();
    }

    public static float callWeakPercentage(Context context) {
        //間違えやすい問題のボーダーのパーセンテージ
        return callUserPreference(context).getFloat("percentage", 30f);
    }

    public static void saveWeakPercentage(Context context, float percentage){
        SharedPreferences.Editor editor = callUserPreference(context).edit();
        editor.putFloat("percentage", percentage);
        editor.commit();
    }

    public static int callTutorialMainStep(Context context) {
        //チュートリアルの//チュートリアルでTutorialMainのステップを取得する
        return callTutorialPreference(context).getInt("main_step", 1);
    }

    public static void addTutorialMainStep(Context context) {
        //チュートリアルのmainのstepを一つ足す
        SharedPreferences.Editor editor = callTutorialPreference(context).edit();
        editor.putInt("main_step", callTutorialMainStep(context) + 1);
        editor.commit();
    }
}
