package com.syncinfo.coffeetown.model.json;

import com.google.gson.Gson;
import com.syncinfo.util.Assert;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by mmartins on 2018-02-20.
 */

public class GSONSerializer<T> implements Serializer<T> {
    private final Gson gson;
    private final Class<T> tClass;

    public GSONSerializer(Class<T> tClass) {
        this(null, tClass);
    }

    public GSONSerializer(Gson gson, Class<T> tClass) {
        if (null == gson) {
            this.gson = new Gson();
        }
        else {
            this.gson = gson;
        }

        this.tClass = tClass;
    }

    @Override
    public Writer serialize(Writer writer, T t) throws IOException {
        Assert.notNull(writer, "OutputStream");
        gson.toJson(t, writer);
        return writer;
    }
}
