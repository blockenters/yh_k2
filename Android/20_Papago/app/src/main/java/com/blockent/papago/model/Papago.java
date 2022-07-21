package com.blockent.papago.model;

import java.io.Serializable;

public class Papago implements Serializable {

    public String text;
    public String translatedText;

    public Papago(String text, String translatedText) {
        this.text = text;
        this.translatedText = translatedText;
    }
}
