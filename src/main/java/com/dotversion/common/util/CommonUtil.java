package com.dotversion.common.util;

// TODO: Use Apache Commons instead?
public class CommonUtil {
    public static boolean equalsWithNullCheck(Object obj1, Object obj2) {
        if (obj1 == null) {
            return (obj2 == null);
        }
        else {
            return (obj2 == null ? false:obj1.equals(obj2));
        }
    }

}
