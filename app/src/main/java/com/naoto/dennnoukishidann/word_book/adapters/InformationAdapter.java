package com.naoto.dennnoukishidann.word_book.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.classes.InformationText;

/**
 * Created by gotounaoto on 2018/02/02.
 */

public class InformationAdapter extends ArrayAdapter<InformationText> {

    Context context;
    LayoutInflater layoutInflater;
    int resource;

    public InformationAdapter(@NonNull Context context, int resource) {
        //コンストラクタ
        super(context, resource);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InformationAdapter.ViewSetUp view_set_up;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, null);
            view_set_up = new InformationAdapter.ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((InformationAdapter.ViewSetUp) convertView.getTag());
        }
        InformationText item = getItem(position);
        view_set_up.text_title.setText(item.getFirst_text());
        view_set_up.text_sub_title.setText(item.getSecond_text());
        view_set_up.text_sub_title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, item.getText_size());
        //上のコードでテキストビューのサイズを設定
        return convertView;
    }

    private class ViewSetUp {

        TextView text_title;
        TextView text_sub_title;

        public ViewSetUp(View view) {
            text_title = (TextView) view.findViewById(R.id.text_title);
            text_sub_title = (TextView) view.findViewById(R.id.text_sub_title);
        }
    }
}
