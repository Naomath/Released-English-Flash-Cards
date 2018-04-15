package com.naoto.dennnoukishidann.word_book.classes;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gotounaoto on 2018/04/15.
 */

public class User {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String user_path;
    //上のpathはfreibase上で使うやつでヤンすよ

    @Getter
    @Setter
    private List<String> book_paths;

    public User() {
        //empty constructor
    }

    public User(String name, String user_path) {
        this.name = name;
        this.user_path = user_path;
    }
}
