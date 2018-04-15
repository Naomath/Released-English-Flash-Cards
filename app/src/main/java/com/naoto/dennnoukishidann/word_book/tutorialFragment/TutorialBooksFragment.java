package com.naoto.dennnoukishidann.word_book.tutorialFragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.activities.WordsActivity;
import com.naoto.dennnoukishidann.word_book.adapters.BooksAdapter;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogAddBookFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.interfaces.OnIntentWordsListener;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.List;

public class TutorialBooksFragment extends Fragment {

    View view;
    ListView listView;
    BooksAdapter adapter;
    ShowcaseView showcaseView;
    FloatingActionButton floatingActionButton;
    CustomDialogAddBookFragment dialogAddFragment;

    public TutorialBooksFragment() {
        // Required empty public constructor
    }

    public static TutorialBooksFragment newInstance() {
        TutorialBooksFragment fragment = new TutorialBooksFragment();
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
        settingFab();
        settingListView();
        addItems();
        settingShowcaseView();
        return view;
    }

    public void addItems() {
        List<Book> items = Book.listAll(Book.class);
        adapter.addAll(items);
    }

    public void settingThisView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_tutorial_books, container, false);
    }

    public void settingFab() {
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showcaseView.hide();
                dialogAddFragment = new CustomDialogAddBookFragment();
                dialogAddFragment.show(getFragmentManager(), "add");
            }
        });
    }


    public void settingListView() {
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new BooksAdapter(getActivity(), R.layout.adapter_books);
        listView.setAdapter(adapter);
        if (CallSharedPreference.callTutorialMainStep(getActivity()) == 3) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Book item = (Book) adapter.getItem(i);
                    long id = item.getId();
                    Intent intent = new Intent(getActivity(), WordsActivity.class);
                    intent.putExtra("id", id);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }
            });
        }
    }

    public void settingShowcaseView() {
        switch (CallSharedPreference.callTutorialMainStep(getActivity())) {
            case 1:
                showcaseView = new ShowcaseView.Builder(getActivity())
                        .setTarget(new ViewTarget(floatingActionButton))
                        .setContentTitle("単語帳の登録")
                        .setContentText("最初から入ってる単語帳『重要な動詞』がありますね。まずはこのプラスのボタンを押してください。そして単語帳のタイトルを入力して、ボタンを押してください。")
                        .withMaterialShowcase()
                        .setStyle(R.style.CustomShowcaseTheme)
                        .doNotBlockTouches() //ShowcaseView下のボタンを触れるように。
                        .build();
                showcaseView.hideButton();
                break;
            case 3:
                showcaseView = new ShowcaseView.Builder(getActivity())
                        .setTarget(new ViewTarget(listView))
                        .setContentTitle("単語帳のアップロード")
                        .setContentText("今から単語帳をアップロードしようと思います。まずアップロードしたい単語帳を選んでください。")
                        .withMaterialShowcase()
                        .doNotBlockTouches() //ShowcaseView下のボタンを触れるように。
                        .build();
                showcaseView.hideButton();
                break;
        }
    }
}
