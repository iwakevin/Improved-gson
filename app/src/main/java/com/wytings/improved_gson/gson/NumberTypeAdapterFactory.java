package com.wytings.improved_gson.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by rex on 12/18/16.
 */

public class NumberTypeAdapterFactory {

    public static <N> TypeAdapterFactory newFactory(
            final Class<N> unBoxed, final Class<N> boxed, final TypeAdapter<? super N> typeAdapter) {
        return new TypeAdapterFactory() {
            @SuppressWarnings("unchecked")
            @Override
            public <M> TypeAdapter<M> create(Gson gson, TypeToken<M> typeToken) {
                Class<? super M> rawType = typeToken.getRawType();
                return (rawType == unBoxed || rawType == boxed) ? (TypeAdapter<M>) typeAdapter : null;
            }
        };
    }

    public static final TypeAdapter<Number> NUMBER = new NumberTypeAdapter() {
        @Override
        public Number read(JsonReader in) throws IOException {
            return new LazyNumber(in.nextString());
        }
    };
    public static final TypeAdapter<Number> INTEGER = new NumberTypeAdapter() {
        @Override
        public Number read(JsonReader in) throws IOException {
            return (int) getDouble(in);
        }
    };
    public static final TypeAdapter<Number> LONG = new NumberTypeAdapter() {
        @Override
        public Number read(JsonReader in) throws IOException {
            return (long) getDouble(in);
        }
    };
    public static final TypeAdapter<Number> FLOAT = new NumberTypeAdapter() {
        @Override
        public Number read(JsonReader in) throws IOException {
            return (float) getDouble(in);
        }
    };
    public static final TypeAdapter<Number> DOUBLE = new NumberTypeAdapter() {
        @Override
        public Number read(JsonReader in) throws IOException {
            return getDouble(in);
        }
    };

    public static abstract class NumberTypeAdapter extends TypeAdapter<Number> {

        public double getDouble(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            if (token == JsonToken.NUMBER) {
                return in.nextDouble();
            } else if (token == JsonToken.STRING) {
                try {
                    return Double.parseDouble(in.nextString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
            return 0;
        }

        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }


    }

}
