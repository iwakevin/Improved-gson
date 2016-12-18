package com.wytings.improved_gson.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by rex on 12/18/16.
 */

public class StringTypeAdapter extends TypeAdapter<String> {

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        out.value(value);
    }

    @Override
    public String read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        if (token == JsonToken.STRING) {
            return in.nextString();
        } else {
            return getJsonString(in);
        }
    }

    private String getJsonString(JsonReader jsonReader) throws IOException {
        JsonToken token = jsonReader.peek();
        switch (token) {
            case BEGIN_ARRAY:
                StringBuilder aBuilder = new StringBuilder();
                aBuilder.append("[");
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    aBuilder.append(getJsonString(jsonReader)).append(",");
                }
                jsonReader.endArray();
                aBuilder.deleteCharAt(aBuilder.length() - 1);
                aBuilder.append("]");
                return aBuilder.toString();
            case BEGIN_OBJECT:
                StringBuilder oBuilder = new StringBuilder();
                oBuilder.append("{");
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    oBuilder.append(getJsonString(jsonReader));
                }
                jsonReader.endObject();
                char last = oBuilder.charAt(oBuilder.length() - 1);
                if (last == ',')
                    oBuilder.deleteCharAt(oBuilder.length() - 1);
                oBuilder.append("}");
                return oBuilder.toString();
            case STRING:
                return "\"" + jsonReader.nextString() + "\",";
            case NUMBER:
                return jsonReader.nextDouble() + ",";
            case BOOLEAN:
                return jsonReader.nextBoolean() + ",";
            case NULL:
                jsonReader.nextNull();
                return "null,";
            case NAME:
                return "\"" + jsonReader.nextName() + "\"=";
            default:
                throw new IllegalStateException("JsonToken = " + token);
        }
    }
}