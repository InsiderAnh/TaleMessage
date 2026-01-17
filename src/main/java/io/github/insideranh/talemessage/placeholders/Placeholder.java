package io.github.insideranh.talemessage.placeholders;

import lombok.Getter;

@Getter
public class Placeholder {

    private final String placeholder;
    private final String value;

    public Placeholder(String placeholder, String value) {
        this.placeholder = placeholder;
        this.value = value;
    }

}