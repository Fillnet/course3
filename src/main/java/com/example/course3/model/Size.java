package com.example.course3.model;

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

}
