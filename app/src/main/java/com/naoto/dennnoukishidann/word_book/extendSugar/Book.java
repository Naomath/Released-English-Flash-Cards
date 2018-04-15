package com.naoto.dennnoukishidann.word_book.extendSugar;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gotounaoto on 2017/12/22.
 */

public class Book extends SugarRecord {

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private long first_id;

    @Getter
    @Setter
    private long last_id;

    @Getter
    @Setter
    private String date;

    @Getter
    @Setter
    private int done_upload;
    //0ならアップロードしてない、1ならアップロードを自分でしたもの、2ならダウンロードしたもの（つまり他人のもの）

    @Getter
    @Setter
    private List<Word> list_words;
    //アップロードの時のためにここに全てのワードを入れる

    @Getter
    @Setter
    private String upload_id;
    //fireBaseで連携を取るためのid

    @Getter
    @Setter
    private int download_time;
    //ダウンロードされた回数を記録するためのやつ

    @Getter
    @Setter
    private String user_name;
    //作成者の名前

    @Getter
    @Setter
    private String book_path;
    //firebaseでもpath（key）

    @Getter
    @Setter
    private String message;
    //firebaseであげるときに必要なやつ
    //その単語帳の説明


    public Book() {
    }//普通のコンストラクタ

    public Book(String title, long first_id, long last_id, String date){
        this.title = title;
        this.first_id = first_id;
        this.last_id = last_id;
        this.date = date;
    }

    public List<Word> returnWords(){
        //このbookの単語を返すメソッド
        List<Word> words = new ArrayList<>();
        for (long i = this.first_id; i <= this.last_id; i++) {
            Word item = Word.findById(Word.class, i);
            //ここでnullチェック
            if(item!=null){
                words.add(item);
            }
        }
        List <AddedWord> addedWordList = SugarRecord.listAll(AddedWord.class);
        //後の方がitemを入れる箱
        for(AddedWord item:addedWordList){
            if(item.getId_group() == this.getId()){
                long word_id = item.getId_word();
                Word itemWord = Word.findById(Word.class, word_id);
                words.add(itemWord);
            }
        }
        return words;
    }

    public List<String> returnListOriginal(int mode) {
        //単語の英語の方だけをリストにして返す
        List<Word> words = new ArrayList<>();
        switch (mode){
            case 0:
                words = returnWords();
                //このアプリ内で、firebaesが絡まないとき
                break;
            case 1:
                words = list_words;
                // firebaseからとってきたのを処理するとき
                break;
        }
        List<String> originals = new ArrayList<>();
        for(Word item:words){
            originals.add(item.getOriginal());
        }
        return originals;
    }
}
