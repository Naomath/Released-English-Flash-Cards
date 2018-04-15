package com.naoto.dennnoukishidann.word_book.processings;

import com.naoto.dennnoukishidann.word_book.extendSugar.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gotounaoto on 2018/02/12.
 */

public class GettingList {

    public static List<Word> gettingDefaultBook() {
        //デフォルト用の単語帳を返す処理
        List<Word> words = new ArrayList<>();
        words.add(new Word("取得する", "get", "動詞"));
        words.add(new Word("行く", "go", "動詞"));
        words.add(new Word("持ってる", "have", "動詞"));
        words.add(new Word("作る", "make", "動詞"));
        words.add(new Word("聞く", "hear", "動詞"));
        words.add(new Word("置く", "put", "動詞"));
        words.add(new Word("思う", "think", "動詞"));
        words.add(new Word("感じる", "feel", "動詞"));
        words.add(new Word("話す","talk","動詞"));
        words.add(new Word("取る","take","動詞"));
        return words;
    }
}
