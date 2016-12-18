package com.wytings.improved_gson.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;

import java.lang.reflect.Type;
import java.util.Collections;

import static com.wytings.improved_gson.gson.NumberTypeAdapterFactory.DOUBLE;
import static com.wytings.improved_gson.gson.NumberTypeAdapterFactory.FLOAT;
import static com.wytings.improved_gson.gson.NumberTypeAdapterFactory.INTEGER;
import static com.wytings.improved_gson.gson.NumberTypeAdapterFactory.LONG;
import static com.wytings.improved_gson.gson.NumberTypeAdapterFactory.NUMBER;

/**
 * Created by rex on 12/18/16.
 */

public class JsonUtils {

    private static final Gson gson = makeGson();

    private static Gson makeGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new CustomizedCollectionTypeAdapterFactory(new ConstructorConstructor(Collections.<Type, InstanceCreator<?>>emptyMap())));
        builder.registerTypeAdapterFactory(NumberTypeAdapterFactory.newFactory(int.class, Integer.class, INTEGER));
        builder.registerTypeAdapterFactory(NumberTypeAdapterFactory.newFactory(long.class, Long.class, LONG));
        builder.registerTypeAdapterFactory(NumberTypeAdapterFactory.newFactory(float.class, Float.class, FLOAT));
        builder.registerTypeAdapterFactory(NumberTypeAdapterFactory.newFactory(double.class, Double.class, DOUBLE));
        builder.registerTypeAdapter(Number.class, NUMBER);
        builder.registerTypeAdapter(String.class, new StringTypeAdapter());
        return builder.create();
    }

    public static <T> T parseJson(String json, Type typeOfT) {
        return gson.fromJson(json, typeOfT);
    }


}
