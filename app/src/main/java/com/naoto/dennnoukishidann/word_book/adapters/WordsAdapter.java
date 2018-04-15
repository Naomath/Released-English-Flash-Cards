package com.naoto.dennnoukishidann.word_book.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.naoto.dennnoukishidann.word_book.R;

/**
 * Created by gotounaoto on 2017/12/23.
 */

public class WordsAdapter extends ArrayAdapter<Word> {

    Context context;
    LayoutInflater layoutInflater;
    int resource;

    public WordsAdapter(@NonNull Context context, int resource) {
        //コンストラクタ
        super(context, resource);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WordsAdapter.ViewSetUp view_set_up;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, null);
            view_set_up = new WordsAdapter.ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((WordsAdapter.ViewSetUp) convertView.getTag());
        }
        Word item = getItem(position);
        if (item != null) {
            String text_part = item.getPart().substring(0, 1);
            String text_original = item.getOriginal();
            String text_translated = item.getTranslated();
            view_set_up.part.setText(text_part);
            view_set_up.original.setText(text_original);
            view_set_up.translated.setText(text_translated);
        }
        return convertView;
    }

    private class ViewSetUp {

        TextView part;
        TextView original;
        TextView translated;

        public ViewSetUp(View view) {
            part = (TextView) view.findViewById(R.id.text_part);
            original = (TextView) view.findViewById(R.id.text_original);
            translated = (TextView) view.findViewById(R.id.text_translated);
        }
    }
}
