package com.naoto.dennnoukishidann.word_book.extendSugar;

import com.orm.SugarRecord;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gotounaoto on 2018/01/28.
 */

public class WeakWord extends SugarRecord {

    @Getter
    @Setter
    private long word_id;
    //Wordの方でのid

    public WeakWord(){
        //empty constructor
    }

    public WeakWord(long word_id){
        this.word_id = word_id;
    }

}
