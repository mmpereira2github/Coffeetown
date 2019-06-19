package com.syncinfo.coffeetown.model.json;

import com.google.gson.Gson;
import com.syncinfo.util.Assert;

import java.io.IOException;
import java.io.Reader;

/**
 * Created by mmartins on 2018-02-20.
 */

public class GSONDeserializer<T> implements Deserializer<T> {
    private final Gson gson;
    private final Class<T> tClass;

    public GSONDeserializer(Class<T> tClass) {
        this(null, tClass);
    }

    public GSONDeserializer(Gson gson, Class<T> tClass) {
        if (null == gson) {
            this.gson = new Gson();
        }
        else {
            this.gson = gson;
        }

        this.tClass = tClass;
    }

    @Override
    public T deserialize(Reader reader, T t) throws IOException{
        Assert.notNull(reader, "OutputStream");
        return this.gson.fromJson(reader, tClass);
    }
}
