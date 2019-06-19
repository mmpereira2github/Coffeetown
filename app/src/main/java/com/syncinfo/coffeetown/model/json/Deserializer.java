package com.syncinfo.coffeetown.model.json;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by mmartins on 2018-02-20.
 */

public interface Deserializer<T> {
    default T deserialize(Reader reader) throws IOException { return deserialize(reader, null); }

    T deserialize(Reader reader, T t) throws IOException;

    class Manager {
        private static Map<Class<?>, Deserializer<?>> deserializerMap = new Hashtable<>();

        public static <T> void registerDeserializer(Class<T> tClass, Deserializer<T> tDeserializer) {
            deserializerMap.put(tClass, tDeserializer);
        }

        public static <T> Deserializer<T> getDeserializer(Class<T> tClass) {
            Deserializer<T> result = (Deserializer<T>) deserializerMap.get(tClass);
            if (result == null) {
                result = new GSONDeserializer<T>(new Gson(), tClass);
                deserializerMap.put(tClass, result);
            }

            return  result;
        }
    }
}
