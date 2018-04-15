package com.naoto.dennnoukishidann.word_book.mainFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogAddBookFragment;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogSortFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.adapters.BooksAdapter;
import com.naoto.dennnoukishidann.word_book.interfaces.OnIntentWordsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BooksFragment extends Fragment implements View.OnClickListener {

    View view;
    ListView listView;
    BooksAdapter adapter;
    SearchView searchView;
    CustomDialogSortFragment dialog;

    public BooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_books, container, false);
        setHasOptionsMenu(true);
        //ここでmenuを持つことを宣言
        settingFab();
        settingListView();
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.fab:
                    makeDialog();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Activity.RESULT_OK) {
            switch (resultCode) {
                case 0:
                    if (resultCode != Activity.RESULT_OK) {
                        return;
                    }
                    //新しい順に
                    sortBooks(true, "");
                    dialog.dismiss();
                    return;
                case 1:
                    if (resultCode != Activity.RESULT_OK) {
                        return;
                    }
                    //古い順に
                    sortBooks(false, "");
                    dialog.dismiss();
                    return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_books, menu);
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_sort).setVisible(true);
        settingSearchView(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                searchView.setIconified(false);
                adapter.clear();
                break;
            case R.id.action_sort:
                dialog = new CustomDialogSortFragment();
                dialog.setTargetFragment(this, 0);
                dialog.show(getFragmentManager(), "sort");
                break;
        }
        return true;
    }

    public void makeDialog() {
        CustomDialogAddBookFragment dialogAddFragment = new CustomDialogAddBookFragment();
        dialogAddFragment.show(getFragmentManager(), "add");
    }

    public void settingFab() {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);
    }


    public void settingListView() {
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new BooksAdapter(getActivity(), R.layout.adapter_books);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book item = (Book) adapter.getItem(i);
                long id = item.getId();
                OnIntentWordsListener listener = (OnIntentWordsListener) getActivity();
                listener.moveToWords(id);
            }
        });
        sortBooks(true, "");
    }

    public void settingSearchView(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                sortBooks(true, s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                sortBooks(true, s);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                sortBooks(true, "");
                return false;
            }
        });
    }

    public void sortBooks(boolean which, String source) {
        //リストをソートするの
        adapter.clear();
        List<Book> books = Book.listAll(Book.class);
        if (source.equals("")) {
            if (which) {
                Collections.reverse(books);
                //これだと新しい順
            }
            for (Book item : books) {
                adapter.add(item);
            }
        } else {
            List<Book> booksShowed = new ArrayList<>();
            for (Book item : books) {
                if (item.getTitle().contains(source)) {
                    booksShowed.add(item);
                }
            }
            for (Book item : booksShowed) {
                adapter.add(item);
            }
        }
    }
}