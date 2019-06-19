package com.syncinfo.coffeetown.model.json;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by mmartins on 2018-02-20.
 */

public interface Serializer<T> {
    Writer serialize(Writer writer, T t) throws IOException;

    class Manager {
        private static Map<Class<?>, Serializer<?>> serializerMap = new Hashtable<>();

        public static <T> void registerSerializer(Class<T> tClass, Serializer<T> tSerializer) {
            serializerMap.put(tClass, tSerializer);
        }

        public static <T> Serializer<T> getSerializer(Class<T> tClass) {
            Serializer<T> result = (Serializer<T>) serializerMap.get(tClass);
            if (result == null) {
                result = new GSONSerializer<T>(new Gson(), tClass);
                serializerMap.put(tClass, result);
            }

            return  result;
        }
    }
}
