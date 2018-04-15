package com.naoto.dennnoukishidann.word_book.extendSugar;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gotounaoto on 2017/12/19.
 */

public class Word extends SugarRecord {

    @Getter
    @Setter
    private String original;

    @Getter
    @Setter
    private String translated;

    @Getter
    @Setter
    private String part;

    @Getter
    @Setter
    private int number_question;
    //この単語の出題数

    @Getter
    @Setter
    private int number_mistake;
    //この単語を間違えた回数

    @Getter
    @Setter
    private float proportion;
    //上の三つの変数は割合を計算するためにあえてfloatに
    //してある。実験済み
    //単語の誤答率

    @Getter
    @Setter
    private int exist_weak;
    //間違えやすい問題に登録されているか
    //1で登録されてるようになる

    @Getter
    @Setter
    private long weak_id;
    //間違えやすい単語として登録されている場合
    //のWeakWordとしてのid


    public Word() {//普通のコンストラクタ　
    }

    public Word(String original, String translated, String part) {
        this.original = original;
        this.translated = translated;
        this.part = part;
    }

    public void calculateProportion() {
        //誤答率を計算する
        float question = new Integer(number_question).floatValue();
        float mistake = new Integer(number_mistake).floatValue();
        this.proportion = mistake / question * 100f;
        if (this.number_mistake == 0) {
            this.proportion = 0f;
        }
    }
}
