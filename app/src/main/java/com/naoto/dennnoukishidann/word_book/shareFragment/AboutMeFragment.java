package com.naoto.dennnoukishidann.word_book.shareFragment;

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
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.processings.FirebaseProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;

import java.util.List;

public class AboutMeFragment extends Fragment implements FirebaseProcessing.OnReturnBookItemListener {

    View view;
    BooksAdapter uploaded_adapter;
    BooksAdapter downloaded_adapter;

    public AboutMeFragment() {
        // Required empty public constructor
    }

    public static AboutMeFragment newInstance() {
        AboutMeFragment fragment = new AboutMeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settingThisView(inflater, container);
        settingListView();
        addBooks();
        return view;
    }

    public void addBooks() {
        //list_viewに該当するbookを選定し、入れていく¥
        List<Book> books = Book.listAll(Book.class);
        int number_uploaded = 0;
        int number_downloaded = 0;
        for (Book item : books) {
            switch (item.getDone_upload()) {
                case 1:
                    uploaded_adapter.add(item);
                    number_uploaded++;
                    break;
                case 2:
                    downloaded_adapter.add(item);
                    number_downloaded++;
                    break;
            }
        }
        if (number_uploaded == 0) {
            TextView uploaded_text = (TextView) view.findViewById(R.id.text_uploaded);
            uploaded_text.setVisibility(View.GONE);
        }
        if (number_downloaded == 0) {
            TextView downloaded_text = (TextView) view.findViewById(R.id.text_downloaded);
            downloaded_text.setVisibility(View.GONE);
        }
    }

    public void clickItem(Book item) {
        //list_viewのitemがクリックされた時の処理
        FirebaseProcessing.OnReturnBookItemListener onReturnBookItemListener = (FirebaseProcessing.OnReturnBookItemListener)this;
        FirebaseProcessing.gettingULBook(item, onReturnBookItemListener);
        //ここでactivityが遷移する
        //このあとreturnItemで処理が行われる
    }

    public void settingThisView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_about_me, container, false);
    }

    public void settingListView() {
        ListView uploaded_list = (ListView) view.findViewById(R.id.list_uploaded);
        ListView downloaded_list = (ListView) view.findViewById(R.id.list_downloaded);
        uploaded_adapter = new BooksAdapter(getActivity(), R.layout.adapter_books);
        downloaded_adapter = new BooksAdapter(getActivity(), R.layout.adapter_books);
        uploaded_list.setAdapter(uploaded_adapter);
        downloaded_list.setAdapter(downloaded_adapter);
        uploaded_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book item = (Book) uploaded_adapter.getItem(i);
                clickItem(item);
            }
        });
    }

    @Override
    public void returnBookItem(Book item) {
        //これはfirebaseの奴が流れてきた時の処理
        IntentProcessing.fromAboutMeToUlInformation(getActivity(), item);
    }
}