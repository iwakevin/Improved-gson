package com.wytings.improved_gson.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Created by rex on 12/18/16.
 */

public class CustomizedCollectionTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public CustomizedCollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();

        Class<? super T> rawType = typeToken.getRawType();
        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }

        Type elementType = $Gson$Types.getCollectionElementType(type, rawType);
        TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
        ObjectConstructor<T> constructor = constructorConstructor.get(typeToken);

        @SuppressWarnings({"unchecked", "rawtypes"})
        TypeAdapter<T> result = new CustomizedCollectionTypeAdapterFactory.Adapter(gson, elementType, elementTypeAdapter, constructor);
        return result;
    }

    private static final class Adapter<E> extends TypeAdapter<Collection<E>> {
        private final TypeAdapter<E> elementTypeAdapter;
        private final ObjectConstructor<? extends Collection<E>> constructor;

        public Adapter(Gson context, Type elementType,
                       TypeAdapter<E> elementTypeAdapter,
                       ObjectConstructor<? extends Collection<E>> constructor) {
            this.elementTypeAdapter =
                    new RuntimeTypeAdapterWrapper<E>(context, elementTypeAdapter, elementType);
            this.constructor = constructor;
        }

        @Override
        public Collection<E> read(JsonReader in) throws IOException {
            Collection<E> collection = constructor.construct();
            if (in.peek() != JsonToken.BEGIN_ARRAY) {
                in.skipValue();
                return collection;
            }


            in.beginArray();
            while (in.hasNext()) {
                E instance = elementTypeAdapter.read(in);
                collection.add(instance);
            }
            in.endArray();
            return collection;
        }

        @Override
        public void write(JsonWriter out, Collection<E> collection) throws IOException {
            if (collection == null) {
                out.nullValue();
                return;
            }

            out.beginArray();
            for (E element : collection) {
                elementTypeAdapter.write(out, element);
            }
            out.endArray();
        }
    }
}