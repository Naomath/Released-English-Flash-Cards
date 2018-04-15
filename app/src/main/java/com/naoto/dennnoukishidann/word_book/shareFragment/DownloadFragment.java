package com.naoto.dennnoukishidann.word_book.shareFragment;

import android.os.Bundle;
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
import android.widget.TextView;

import com.naoto.dennnoukishidann.word_book.adapters.UserAdapter;
import com.naoto.dennnoukishidann.word_book.classes.User;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.adapters.BooksAdapter;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;
import com.naoto.dennnoukishidann.word_book.processings.FirebaseProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class DownloadFragment extends Fragment implements FirebaseProcessing.OnAddItemListener {

    View view;
    SearchView searchView;
    BooksAdapter adapter;
    UserAdapter userAdapter;
    ShowcaseView showcaseView;

    public DownloadFragment() {
        // Required empty public constructor
    }

    public static DownloadFragment newInstance() {
        DownloadFragment fragment = new DownloadFragment();
        return fragment;
    }

    @Override
    public void addBookItem(Book item) {
        adapter.add(item);
    }

    @Override
    public void addUserItem(User item) {
        userAdapter.add(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_down_load, container, false);
        setHasOptionsMenu(true);
        settingListView();
        settingShowcaseView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_books, menu);
        menu.findItem(R.id.action_search).setVisible(true);
        settingSearchView(menu);
        settingListener();
        searchBooksHigher();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                actionSearch();
                break;
        }
        return true;
    }

    public void actionSearch() {
        //searchviewのiconが押された時の処理
        adapter.clear();
        //ここでadapterをクリアにする
    }

    public void intentToBookInformation(Book item) {
        //DownloadActivityに遷移するためのメソッド
        IntentProcessing.ToDLBookInformation(getActivity(), item.getBook_path(), 0, null
        );
    }

    public void intentToUserInformation(User item) {
        IntentProcessing.toUserInformation(getActivity(), item);
    }

    public void settingSearchView(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconifiedByDefault(true);
        searchView.setSubmitButtonEnabled(false);
    }

    public void settingListView() {
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new BooksAdapter(getActivity(), R.layout.adapter_download_books);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book item = (Book) adapter.getItem(i);
                if (showcaseView != null) {
                    showcaseView.hide();
                }
                intentToBookInformation(item);
            }
        });
        //ここから下はユーザーのリストビュー
        ListView user_list = (ListView) view.findViewById(R.id.user_list);
        userAdapter = new UserAdapter(getActivity(), R.layout.adapter_user);
        user_list.setAdapter(userAdapter);
        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User item = (User) userAdapter.getItem(i);
                intentToUserInformation(item);
            }
        });
    }

    public void settingListener() {
        //いろんなリスナーの設定をする
        //まずはsearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchBooksKeyword(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //ここにも処理を書いて、調べる語句が変わったたびに
                //調べるようにするとネットワークの時間がかかるし
                //大変なので何も書かずにnullにしておく
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                searchBooksHigher();
                return false;
            }
        });
        searchView.setOnClickListener(new SearchView.OnClickListener() {

            @Override
            public void onClick(View view) {
                adapter.clear();
            }
        });
    }

    public void settingShowcaseView() {
        if (CallSharedPreference.callTutorialMainStep(getActivity()) == 4) {
            showcaseView = new ShowcaseView.Builder(getActivity())
                    .setTarget(new ViewTarget(view.findViewById(R.id.list_view)))
                    .setContentTitle("単語帳のダウンロード")
                    .setContentText("ではまずダウンロードしたい単語帳を選んでタッチしてください。")
                    .setStyle(R.style.CustomShowcaseTheme)
                    .withMaterialShowcase()
                    .doNotBlockTouches() //ShowcaseView下のボタンを触れるように。
                    .build();
            showcaseView.hideButton();
        }
    }

    public void searchBooksHigher() {
        //ダウンロード数Top20を検索する時
        adapter.clear();
        visibleTextView();
        FirebaseProcessing firebaseProcessing = new FirebaseProcessing(this);
        firebaseProcessing.startSearchHigher();
    }

    public void searchBooksKeyword(String keyword) {
        //キーワードで検索する時
        adapter.clear();
        invisibleTextView();
        FirebaseProcessing firebaseProcessing = new FirebaseProcessing(this);
        firebaseProcessing.startSearchKeyword(keyword);

    }

    public void visibleTextView() {
        //TextViewを可視化する
        TextView textView = (TextView) view.findViewById(R.id.text_popular);
        textView.setVisibility(View.VISIBLE);
    }

    public void invisibleTextView() {
        //textviewを見えなくする
        TextView textView = (TextView) view.findViewById(R.id.text_popular);
        textView.setVisibility(View.INVISIBLE);
    }

}