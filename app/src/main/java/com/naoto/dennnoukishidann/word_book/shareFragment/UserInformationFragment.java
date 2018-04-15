package com.naoto.dennnoukishidann.word_book.shareFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.adapters.BooksAdapter;
import com.naoto.dennnoukishidann.word_book.classes.User;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.processings.BundleProcessing;
import com.naoto.dennnoukishidann.word_book.processings.FirebaseProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;

import java.util.List;

public class UserInformationFragment extends Fragment implements FirebaseProcessing.OnReturnUserItemListener,
        FirebaseProcessing.OnReturnBookItemListener {

    View view;
    User user;
    List<String> items;
    BooksAdapter booksAdapter;

    public UserInformationFragment() {
        // Required empty public constructor
    }

    public static UserInformationFragment newInstance() {
        UserInformationFragment fragment = new UserInformationFragment();
        return fragment;
    }

    @Override
    public void returnUserItem(User item) {
        this.user = item;
        addItems();
    }

    @Override
    public void returnBookItem(Book item) {
        booksAdapter.add(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gettingUser();
        settingThisView(inflater, container);
        settingTextView();
        settingListView();
        return view;
    }

    public void addItems() {
        FirebaseProcessing.OnReturnBookItemListener onReturnBookItemListener = (FirebaseProcessing.OnReturnBookItemListener) this;
        FirebaseProcessing firebaseProcessing = new FirebaseProcessing(onReturnBookItemListener);
        for (String path : user.getBook_paths()) {
            firebaseProcessing.startSearchBookPath(path);
        }
    }

    public void gettingUser() {
        items = BundleProcessing.inUserInformationFr(this);
        FirebaseProcessing.OnReturnUserItemListener onReturnUserItemListener = (FirebaseProcessing.OnReturnUserItemListener) this;
        FirebaseProcessing firebaseProcessing = new FirebaseProcessing(onReturnUserItemListener);
        firebaseProcessing.startSearchUserPath(items.get(1));
    }

    public void settingListView() {
        ListView listView = (ListView) view.findViewById(R.id.book_list);
        booksAdapter = new BooksAdapter(getActivity(), R.layout.adapter_books);
        listView.setAdapter(booksAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book item = (Book) booksAdapter.getItem(i);
                IntentProcessing.ToDLBookInformation(getActivity(), item.getBook_path(), 1, user);
            }
        });
    }

    public void settingTextView() {
        TextView textView = (TextView) view.findViewById(R.id.name_text);
        textView.setText(items.get(0));
    }

    public void settingThisView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_user_information, container, false);
    }

}