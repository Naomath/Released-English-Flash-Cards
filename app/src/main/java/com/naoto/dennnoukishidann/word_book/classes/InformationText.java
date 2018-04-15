package com.naoto.dennnoukishidann.word_book.classes;

import com.naoto.dennnoukishidann.word_book.adapters.InformationAdapter;

import lombok.Getter;
import lombok.Setter;

public class InformationText {

    //SettingUserAdapterで使う

    @Getter
    @Setter
    private String first_text;

    @Getter
    @Setter
    private String second_text;

    @Getter
    @Setter
    private int id;
    //adapterから取得したときのidを示すためのやつ

    @Getter
    @Setter
    private float text_size;

    public InformationText(String first_text, String second_text, int id, float text_size){
        this.first_text = first_text;
        this.second_text = second_text;
        this.id = id;
        this.text_size = text_size;
    }

    public InformationText(String first_text, String second_text, float text_size){
        this.first_text = first_text;
        this.second_text = second_text;
        this.text_size = text_size;
    }

}
