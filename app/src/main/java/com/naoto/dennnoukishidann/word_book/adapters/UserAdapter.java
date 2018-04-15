package com.naoto.dennnoukishidann.word_book.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.classes.User;

/**
 * Created by gotounaoto on 2018/04/15.
 */

public class UserAdapter extends ArrayAdapter<User> {

    Context context;
    LayoutInflater layoutInflater;
    int resource;


    public UserAdapter(@NonNull Context context, int resource) {
        //コンストラクタ
        super(context, resource);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserAdapter.ViewSetUp view_set_up;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, null);
            view_set_up = new UserAdapter.ViewSetUp(convertView);
            convertView.setTag(view_set_up);
        } else {
            view_set_up = ((UserAdapter.ViewSetUp) convertView.getTag());
        }
        User item = getItem(position);
        if (item != null) {
            view_set_up.user.setText(item.getName());
        }
        return convertView;
    }

    private class ViewSetUp {

        TextView user;

        public ViewSetUp(View view) {
            user = (TextView) view.findViewById(R.id.user_text);
        }
    }
}
