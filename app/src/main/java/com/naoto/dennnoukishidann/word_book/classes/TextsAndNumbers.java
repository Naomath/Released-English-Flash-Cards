package com.naoto.dennnoukishidann.word_book.classes;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by gotounaoto on 2018/04/05.
 */

public class TextsAndNumbers {

    @Getter
    @Setter
    private List<String> texts;

    @Getter
    @Setter
    private List<Integer> numbers;

    public TextsAndNumbers() {
        //empty constructor
    }

    public TextsAndNumbers(List<String> texts, List<Integer> numbers){
        this.texts = texts;
        this.numbers = numbers;
    }
}
