package com.datn.dongho5s.Entity;

public class StatusValue {
    private int value;
    private String label;

    public StatusValue(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
