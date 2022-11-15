package com.bill.project.model;

import lombok.Data;

import java.util.Arrays;

@Data
public class Card {
    private final int id;
    private final String value;
    private final String suit;

    public int getFaceValue() {
        if ("ACE".equals(value)) {
            return 1;
        }
        if (Arrays.asList("KING", "QUEEN", "JACK").contains(value)) {
            return 10;
        }
        return Integer.valueOf(value);
    }
}
