package com.syncinfo.android.coffeetown.view;

import android.content.Intent;

import com.syncinfo.android.coffeetown.MainActivity;
import com.syncinfo.coffeetown.model.Table;
import com.syncinfo.coffeetown.model.Waiter;
import com.syncinfo.coffeetown.model.json.Deserializer;
import com.syncinfo.coffeetown.model.json.Serializer;
import com.syncinfo.util.Assert;
import com.syncinfo.util.Matcher;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by mmartins on 2018-02-19.
 */

public class IntentHelper {
    public static final String RESULT_CODE_EXPECTED = "ResultCodeExpected";
    private Intent intent = null;

    public IntentHelper() { this.intent = new Intent(); }
    public IntentHelper(Intent intent) { this.intent = intent == null? new Intent(): intent; }

    public <F extends android.support.v4.app.Fragment> IntentHelper setTargetFragment(Class<F> fragmentClass) {
        this.intent.putExtra(MainActivity.FRAGMENT_CLASS, fragmentClass.getName());
        return this;
    }

    public Intent intent() { return this.intent; }

    public IntentHelper setResultCodeExpected(int resultCodeExpected) {
        this.intent.putExtra(RESULT_CODE_EXPECTED, resultCodeExpected);
        return this;
    }

    public boolean expectResultCode() {
        return this.intent.hasExtra(RESULT_CODE_EXPECTED);
    }

    public int getResultCodeExpected() {
        int result = this.intent.getIntExtra(RESULT_CODE_EXPECTED, -1);
        Assert.isTrue( result > 0,
                () -> { throw new IllegalStateException("No result code expected");});
        return result;
    }

    public <T> IntentHelper serialize(T objects[]) {
        if (null != objects) {
            String array[] = new String[objects.length];
            StringWriter stringWriter;
            try {
                Serializer<T> objectSerializer = Serializer.Manager.getSerializer((Class<T>)objects.getClass().getComponentType());
                int i = 0;
                for (T t: objects) {
                    stringWriter = new StringWriter();
                    objectSerializer.serialize(stringWriter, t);
                    array[i++] = stringWriter.toString();
                }

            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

            this.intent.putExtra(objects.getClass().getComponentType().getName(), array);
        }

        return this;
    }

    public <T> IntentHelper serialize(T object) {
        if (null != object) {
            StringWriter stringWriter = new StringWriter();
            try {
                Serializer<T> objectSerializer = Serializer.Manager.getSerializer((Class<T>)object.getClass());
                objectSerializer.serialize(stringWriter, object);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

            this.intent.putExtra(object.getClass().getName(), stringWriter.toString());
        }

        return this;
    }

    public <T> T deserialize(Class<T> tClass) {
        if (null != tClass) {
            String json = this.intent.getStringExtra(tClass.getName());
            if (null != json) {
                StringReader stringReader = new StringReader(json);
                try {
                    Deserializer<T> objectDeserializer = Deserializer.Manager.getDeserializer(tClass);
                    return objectDeserializer.deserialize(stringReader, null);
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        return null;
    }

    public <T> T[] deserialize(T array[]) {
        T[] result= array;
        if (null != array) {
            String json[] = this.intent.getStringArrayExtra(array.getClass().getComponentType().getName());
            if (null != json) {
                try {
                    Class<T> tClass = (Class<T>) array.getClass().getComponentType();
                    Deserializer<T> objectDeserializer = Deserializer.Manager.getDeserializer(tClass);
                    int i = 0;
                    for (String s: json) {
                        result[i++] = objectDeserializer.deserialize(new StringReader(s), null);
                    }

                    return array;
                } catch (IOException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        return result;
    }

//    public IntentHelper setTable(int id) {
//        this.intent.putExtra(Table.class.getName(), id);
//        return this;
//    }
//
//    public IntentHelper setTable(Table table) {
//        return setTable(Assert.notNull(table, "Table").getId());
//    }
//
//    public Integer getWaiterId(boolean mandatory) {
//        return getInteger(Waiter.class.getName(), mandatory, 0, i -> i > 0);
//    }
//
//    public Integer getInteger(String name, boolean mandatory, int defaultValue, Matcher<Integer> validValueRule) {
//        if (this.intent.hasExtra(name)) {
//            Integer result = this.intent.getIntExtra(name, defaultValue);
//            if (validValueRule.matches(result) == false) {
//                throw new IllegalArgumentException(name + " = " + result + " is invalid");
//            }
//            else {
//                return result;
//            }
//        }
//        else if (mandatory) {
//            throw new IllegalArgumentException(name + " is mandatory");
//        }
//
//        return null;
//    }
}
