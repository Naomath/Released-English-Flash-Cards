package com.naoto.dennnoukishidann.word_book.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.R;

/**
 * Created by gotounaoto on 2018/01/03.
 */

public class BooksAdapter extends ArrayAdapter<Book>{
    Context context;
    LayoutInflater layoutInflater;
    int resource;


    public BooksAdapter(@NonNull Context context, int resource) {
        //コンストラクタ
        super(context, resource);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BooksAdapter.ViewSetUp view_set_up;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, null);
            view_set_up = new BooksAdapter.ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((BooksAdapter.ViewSetUp) convertView.getTag());
        }
        Book item = getItem(position);
        if (item != null) {
            String title = item.getTitle();
            String calendar = item.getDate();
            view_set_up.title.setText(title);
            view_set_up.calendar.setText(calendar);
        }
        return convertView;
    }

    private class ViewSetUp {

        TextView title;
        TextView calendar;
        public ViewSetUp(View view) {
            title = (TextView)view.findViewById(R.id.title_text);
            calendar = (TextView)view.findViewById(R.id.calendar_text);
        }
    }
}

