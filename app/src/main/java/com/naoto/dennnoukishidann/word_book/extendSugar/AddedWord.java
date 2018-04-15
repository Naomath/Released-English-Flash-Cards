package com.naoto.dennnoukishidann.word_book.extendSugar;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gotounaoto on 2018/01/22.
 */

public class AddedWord extends SugarRecord {

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
    private long id_group;

    @Getter
    @Setter
    private long id_word;
    //これはWords.classにおいてのid

    public AddedWord(){
        //普通のコンストラクタ
    }

    public AddedWord(String original, String translated, String part, long id_group, long id_word){
        this.original = original;
        this.translated = translated;
        this.part = part;
        this.id_group = id_group;
        this.id_word = id_word;
    }
}
