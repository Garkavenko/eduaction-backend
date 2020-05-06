package ru.vsueducation.utils;

import java.util.List;

public class ResponseObject<T> {
    private String text;
    private T[][] table;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public T[][] getTable() {
        return table;
    }

    public void setTable(T[][] table) {
        this.table = table;
    }
}
