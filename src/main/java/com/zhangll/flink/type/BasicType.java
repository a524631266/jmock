package com.zhangll.flink.type;

import java.util.List;

public class BasicType {

    public static boolean isInteger(Class type) {
        return type == Integer.class || type == int.class;
    }

    public static boolean isString(Class type) {
        return type == String.class;
    }
    public static boolean isDouble(Class type) {
        return type == Double.class || type == double.class;
    }
    public static boolean isFloat(Class type) {
        return type == Float.class || type == float.class;
    }
    public static boolean isShort(Class type) {
        return type == Short.class || type == short.class;
    }
    public static boolean isBoolean(Class type) {
        return type == Boolean.class || type == boolean.class;
    }

    public static boolean isLong(Class type) {
        return type == Long.class || type == long.class;
    }
    public static boolean isChar(Class type) {
        return type == char.class || type == Character.class;
    }

    /**
     * 判断是否是基本类型,基本类型（包装类型不是基本类型）
     * @param type
     * @return
     */
    public static boolean isPrimitive(Class type) {
        return type.isPrimitive();
    }
    public static boolean isList(Class type) {
        return List.class.isAssignableFrom(type);
    }

    public static boolean isArray(Object o) {
        return o instanceof List;
    }
}
