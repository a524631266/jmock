package com.zhangll.flink.type;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

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
    public static boolean isCollection(Class type) {
        return List.class.isAssignableFrom(type)||
                Set.class.isAssignableFrom(type);
    }

    public static boolean isArray(Class type) {
        return type.getComponentType() != null;
    }

    public static Class primitiveToWarpper(Class primitive){
        if(isDouble(primitive)){
            return Double.class;
        }else if(isInteger(primitive)){
            return Integer.class;
        }else if(isChar(primitive)){
            return Character.class;
        }else if(isBoolean(primitive)){
            return Boolean.class;
        }else if(isFloat(primitive)){
            return Float.class;
        }else if(isShort(primitive)){
            return Short.class;
        }else if(isLong(primitive)){
            return Long.class;
        }
        return primitive;
    }

    public static Class warpperToPrimitive(Class primitive){
        if(isDouble(primitive)){
            return double.class;
        }else if(isInteger(primitive)){
            return int.class;
        }else if(isChar(primitive)){
            return char.class;
        }else if(isBoolean(primitive)){
            return boolean.class;
        }else if(isFloat(primitive)){
            return float.class;
        }else if(isShort(primitive)){
            return short.class;
        }else if(isLong(primitive)){
            return long.class;
        }
        return primitive;
    }
    public static Object transWrapperArrayToBasicArray(Class<?> componentType, Object[] result) {
        Class<?> componentType1 = result.getClass().getComponentType();
        if(componentType != componentType1){
            Class aClass = warpperToPrimitive(componentType1);
            Object object = Array.newInstance(aClass, result.length);
            Class<?> resultType = object.getClass().getComponentType();
            if(isDouble(resultType)){
                double[] result1 = (double[]) object;
                for (int i = 0; i < result.length; i++) {
                    result1[i] = (double) result[i];
                }
                return result1;
            }else if(isInteger(resultType)){
                int[] result1 = (int[]) object;
                for (int i = 0; i < result.length; i++) {
                    result1[i] = (int) result[i];
                }
                return result1;
            }else if(isChar(resultType)){
                char[] result1 = (char[]) object;
                for (int i = 0; i < result.length; i++) {
                    result1[i] = (char) result[i];
                }
                return result1;
            }else if(isBoolean(resultType)){
                boolean[] result1 = (boolean[]) object;
                for (int i = 0; i < result.length; i++) {
                    result1[i] = (boolean) result[i];
                }
                return result1;
            }else if(isFloat(resultType)){
                float[] result1 = (float[]) object;
                for (int i = 0; i < result.length; i++) {
                    result1[i] = (float) result[i];
                }
                return result1;
            }else if(isShort(resultType)){
                short[] result1 = (short[]) object;
                for (int i = 0; i < result.length; i++) {
                    result1[i] = (short) result[i];
                }
                return result1;
            }else if(isLong(resultType)){
                long[] result1 = (long[]) object;
                for (int i = 0; i < result.length; i++) {
                    result1[i] = (long) result[i];
                }
                return result1;
            }
//            for (int i = 0; i < result.length; i++) {
//                o[i] = result[i];
//            }
            return result;
        }
        return result;
    }
}
