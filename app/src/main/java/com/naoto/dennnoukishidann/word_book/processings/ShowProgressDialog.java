package com.naoto.dennnoukishidann.word_book.processings;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by gotounaoto on 2018/03/14.
 */

public class ShowProgressDialog {

    ProgressDialog progressDialog;

    Context context;

    public ShowProgressDialog(){}

    public ShowProgressDialog(Context context) {
        this.context = context;
    }

    public void show(){
        //ダイアログを表示する
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void dismiss(){
        //ダイアログを終わりにする
        progressDialog.dismiss();
    }
}
