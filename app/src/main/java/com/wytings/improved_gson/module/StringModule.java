package com.wytings.improved_gson.module;

/**
 * Created by rex on 12/18/16.
 */

public class StringModule {
    public int code;
    public String data;
    public String msg;

    @Override
    public String toString() {
        return "StringModule{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
