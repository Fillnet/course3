package com.example.course3.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Size {
    L(44),

    M(40),

    S(35),

    XL(48),

    XS(39);

    private final int valueSize;

    Size(int valueSize) {
        this.valueSize = valueSize;
    }
//
//    @JsonValue
//
//    public int getValueSize() {
//        return getValueSize();
//    }
//
//    @JsonCreator
//    public Size forValues(int valueSize) {
//        for (Size s : Size.values()) {
//            if (s.valueSize == valueSize) {
//                return s;
//            }
//        }
//        return null;
//    }
}
