package com.wytings.improved_gson.module;

import java.util.List;

/**
 * Created by rex on 12/18/16.
 */

public class ListModule {

    public int code;
    public List<String> data;
    public String msg;

    @Override
    public String toString() {
        return "ListModule{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
