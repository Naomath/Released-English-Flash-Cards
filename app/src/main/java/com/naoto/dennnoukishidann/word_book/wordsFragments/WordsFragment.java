package com.naoto.dennnoukishidann.word_book.wordsFragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogOneButtonFragment;
import com.naoto.dennnoukishidann.word_book.processings.BundleProcessing;
import com.naoto.dennnoukishidann.word_book.processings.IntentProcessing;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogDeleteFragment;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogInputMessageFragment;
import com.naoto.dennnoukishidann.word_book.dialogFragment.CustomDialogWordAddFragment;
import com.naoto.dennnoukishidann.word_book.extendSugar.AddedWord;
import com.naoto.dennnoukishidann.word_book.extendSugar.Book;
import com.naoto.dennnoukishidann.word_book.extendSugar.WeakWord;
import com.naoto.dennnoukishidann.word_book.extendSugar.Word;
import com.naoto.dennnoukishidann.word_book.R;
import com.naoto.dennnoukishidann.word_book.adapters.WordsAdapter;
import com.naoto.dennnoukishidann.word_book.processings.CallSharedPreference;
import com.naoto.dennnoukishidann.word_book.processings.FirebaseProcessing;
import com.naoto.dennnoukishidann.word_book.interfaces.OnInputListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class WordsFragment extends Fragment implements View.OnClickListener {

    View view;
    Book book;
    SwipeMenuListView listView;
    WordsAdapter adapter;
    ShowcaseView showcaseView;
    long book_id;
    CustomDialogWordAddFragment dialog;
    CustomDialogInputMessageFragment dialogInputMessageFragment;
    CustomDialogOneButtonFragment dialogOneButtonFragment;
    OnWordsListener onWordsListener;
    public static final String error_message = "アップロードに失敗しました。もう一回してください。";

    public WordsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_words, container, false);
        setHasOptionsMenu(true);
        gettingBookId();
        settingListView();
        settingSwipeMenu();
        settingListener();
        sortWords();
        settingShowcaseView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_words, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    //アップロードの時のメッセージが帰ってきた後の処理
                    saveMessage(data, 0);
                    return;
                case 1:
                    //単語を新たに追加する
                    addWord(data);
                    return;
                case 2:
                    //アップデートの時の処理
                    dialogOneButtonFragment.dismiss();
                    showMessageDialog(3);
                    return;
                case 3:
                    //アップデートの時の処理でmessageが帰ってきた後の処理
                    saveMessage(data, 1);
                    return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                CustomDialogDeleteFragment customDialogDeleteFragment = new CustomDialogDeleteFragment();
                customDialogDeleteFragment.show(getFragmentManager(), "delete");
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.button_upload:
                    judgeDoneUpload();
                    break;
                case R.id.button_study:
                    study();
                    break;
                case R.id.button_add:
                    showWordAddDialog();
                    break;

            }
        }
    }


    public void gettingBookId() {
        OnInputListener listener = (OnInputListener) getActivity();
        book_id = listener.sendLong();
    }

    public void settingListView() {
        listView = (SwipeMenuListView) view.findViewById(R.id.list_view);
        adapter = new WordsAdapter(getActivity(), R.layout.adapter_words);
        listView.setAdapter(adapter);
    }

    public void settingSwipeMenu() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            //横に出るやつ
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xd3, 0x2f, 0x2f)));
                deleteItem.setIcon(R.drawable.ic_delete_white_48dp);
                deleteItem.setWidth(300);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Word item = (Word) adapter.getItem(position);
                        if (item.getExist_weak() == 1) {
                            //もし間違い問題として登録されている場合の処理
                            WeakWord weakWord = WeakWord.findById(WeakWord.class, item.getWeak_id());
                            weakWord.delete();
                        }
                        item.delete();
                        adapter.remove(item);
                        break;
                }
                return false;
            }
        });
    }

    public void settingListener() {
        //リスナーの設定
        view.findViewById(R.id.button_upload).setOnClickListener(this);
        view.findViewById(R.id.button_study).setOnClickListener(this);
        view.findViewById(R.id.button_add).setOnClickListener(this);
        onWordsListener = (OnWordsListener) getActivity();
    }

    public void settingBookBeforeUpload() {
        //uploadする前に単語を入れたりdone_uploadを変えたりとちょっといじる
        book.setDone_upload(2);
        //上のによりfirebase上でアップロードしたことにする
        book.setList_words(book.returnWords());
        //上で単語を入れている。.save()させてないのはリストは保存できなから
        book.setUser_name(CallSharedPreference.callUserName(getActivity()));
    }

    public void settingShowcaseView() {
        if (CallSharedPreference.callTutorialMainStep(getActivity()) == 3) {
            showcaseView = new ShowcaseView.Builder(getActivity())
                    .setTarget(new ViewTarget(view.findViewById(R.id.button_upload)))
                    .setContentTitle("単語帳のアップロード")
                    .setContentText("アップロードボタンを押して、単語帳の説明を書き込んでからアップロードしてください。")
                    .withMaterialShowcase()
                    .doNotBlockTouches() //ShowcaseView下のボタンを触れるように。
                    .build();
            showcaseView.hideButton();
            view.findViewById(R.id.button_study).setClickable(false);
            view.findViewById(R.id.button_add).setClickable(false);
            //ここでクリックできないようにしている
        }
    }

    public void sortWords() {
        book = Book.findById(Book.class, book_id);
        List<Word> source = book.returnWords();
        List<Word> words = new ArrayList<>();
        for (Word item : source) {
            words.add(item);
        }
        //そして最後に全部ぶっこむ
        for (Word item : words) {
            adapter.add(item);
        }
    }

    public void study() {
        //この単語帳を解く処理
        onWordsListener.startStudy(book_id);
    }

    public void showWordAddDialog() {
        //wordを追加するdialogを表示するメソッド
        dialog = new CustomDialogWordAddFragment();
        dialog.setTargetFragment(this, Activity.RESULT_OK);
        dialog.show(getFragmentManager(), "add_word");
    }

    public void showMessageDialog(int mode) {
        //このmodeはアップデートかアップロードかの違い
        //modeはそのままrequestCodeに反映される
        dialogInputMessageFragment = new CustomDialogInputMessageFragment();
        dialogInputMessageFragment.setArguments(BundleProcessing.toInputMessageDialog(book, "説明", "単語帳の説明を入力してください。"
                , "アップロード", mode));
        dialogInputMessageFragment.setTargetFragment(this, Activity.RESULT_OK);
        dialogInputMessageFragment.show(getFragmentManager(), "message");
    }

    public void showOneBtnDialog() {
        dialogOneButtonFragment = new CustomDialogOneButtonFragment();
        dialogOneButtonFragment.setArguments(BundleProcessing.toOneBtnDialog("確認", "この単語帳はすでにアップロードされているので、" +
                "アップデートをすることになりますが,いいですか？", 2));
        dialogOneButtonFragment.setTargetFragment(this, Activity.RESULT_OK);
        dialogOneButtonFragment.show(getFragmentManager(), "check");
    }

    public void judgeDoneUpload() {
        //どのタイプのアップロードなのか判別する
        switch (book.getDone_upload()) {
            case 0:
                //まだアップロードしていないもの
                showMessageDialog(0);
                break;
            case 1:
                //もうアップロードしてあるものよってアップデートすることになる
                showOneBtnDialog();
                break;
            case 2:
                //ダウンロードしたもの。よって自分でアップロードすることはできない
                makeToast("この単語帳はダウンロードしたものなので、アップロードできません。");
                break;
        }
    }

    public void upload() {
        //saveMessageから流れてやってくる
        settingBookBeforeUpload();
        uploadName(0);
        //順番としてはuploadName->uploadBook->uploadBookPathとなる
        //                                |->saveBookKey
    }

    public void uploadName(final int mode) {
        //modeが0でupload, modeが1でupdate
        //名前をアップデート(アップロード？)する
        String user_name = CallSharedPreference.callUserName(this.getActivity());
        //データベースで使うためのユーザーネーム
        String user_id = CallSharedPreference.callUserId(this.getActivity());
        //データベースで使うためのユーザーid
        //keyがuseridになっている
        FirebaseProcessing.gettingUserReference().child(user_id).child("name").setValue(user_name, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //サーバーにcommitした時に呼び出される
                if (databaseError != null) {
                    makeToast(error_message);
                } else {
                    switch (mode) {
                        case 0:
                            uploadBook();
                            break;
                        case 1:
                            updateBook();
                            break;
                    }
                }
            }
        });
    }

    public void uploadBook() {
        //bookをアップロードする
        final String[] book_key = new String[1];
        //bookをuserのほうにパスを保存するため
        //bookのkey
        //配列にしないとしたのメソッドからアクセスできない。finalをつけなければいけないので
        book.setUser_name(CallSharedPreference.callUserName(getActivity()));
        FirebaseProcessing.gettingBookReference().push().setValue(book, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //サーバーにcommitした時に呼び出される
                if (databaseError != null) {
                    makeToast(error_message);
                } else {
                    book_key[0] = databaseReference.getKey();
                    uploadBookPath(book_key[0]);
                }
            }
        });
    }

    public void updateBook() {
        //bookをupdateする
        final String[] book_key = new String[1];
        //bookをuserのほうにパスを保存するため
        //bookのkey
        //配列にしないとしたのメソッドからアクセスできない。finalをつけなければいけないので
        FirebaseProcessing.gettingBookReference().child(book.getBook_path()).setValue(book, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //サーバーにcommitした時に呼び出される
                if (databaseError != null) {
                    makeToast(error_message);
                } else {
                    makeToast("アップデートに成功しました。");
                }
            }
        });
    }

    public void uploadBookPath(String book_key) {
        //userのところにbookのpath(key)を追加する
        String user_id = CallSharedPreference.callUserId(this.getActivity());
        //データベースで使うためのユーザーid
        saveBookKey(book_key);
        //ここで自分のbookにpathを保存する
        FirebaseProcessing.gettingUserReference().child(user_id).child("paths").push().setValue(book_key, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //サーバーにcommitした時に呼び出される
                if (databaseError != null) {
                    makeToast(error_message);
                } else {
                    makeToast("アップロードしました。");
                }
            }
        });
        if (CallSharedPreference.callTutorialMainStep(getActivity()) == 3) {
            CallSharedPreference.addTutorialMainStep(getActivity());
            IntentProcessing.backToTutorialMainWithMessage(getActivity(), "アップロードしました。", 2);
        }
    }

    public void update() {
        //具体的なupdateの処理
        //saveMessage()から流れてやってくる
        settingBookBeforeUpload();
        uploadName(1);
        //順番としてはuploadName->updateBook->
        //                                |->saveBookKey
        //pathは変わらないので心配する必要なし
    }

    public void saveBookKey(String book_path) {
        //bookのfirebase上でのkeyを保存する
        book.setDone_upload(1);
        book.setBook_path(book_path);
        book.save();
    }

    public void saveMessage(Intent data, int mode) {
        //bookのmessageを実際に保存するメソッド
        //modeが0ならupload(), 1ならupdate()
        book.setMessage(IntentProcessing.fromInputMessageDialogInAnyFragment(data));
        dialogInputMessageFragment.dismiss();
        switch (mode) {
            case 0:
                upload();
                break;
            case 1:
                update();
                break;
        }
    }

    public void addWord(Intent data) {
        //実際に単語を追加するメソッド
        String original = data.getStringExtra("original");
        String translated = data.getStringExtra("translated");
        String part = data.getStringExtra("part");
        Word word = new Word(original, translated, part);
        word.save();
        long id_word = word.getId();
        AddedWord addedWord = new AddedWord(original, translated, part, book_id, id_word);
        addedWord.save();
        dialog.dismiss();
        sortWords();
    }

    public void makeToast(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public interface OnWordsListener {
        void startStudy(long id);
    }
}
