package com.syncinfo.util;

/**
 * Created by mmartins on 2018-01-21.
 */

public class Assert {
    public interface Condition {
        boolean isTrue();
    }

    public interface Thrower<E extends Exception> {
        void throwE() throws E;
    }

    static public <ObjectType> ObjectType notNull(ObjectType o, String objectName) {
        return notNull(o, ()->{throw new NullPointerException(objectName + " is null");});
    }

    static public <ObjectType, E extends Exception> ObjectType notNull(ObjectType o, Thrower<E> t) throws E {
        isTrue(null != o, t);
        return o;
    }

    static public <E extends Exception> void isTrue(boolean condition, Thrower<E> t) throws E {
        if (condition == true) {
            return;
        }
        t.throwE();
    }
}
