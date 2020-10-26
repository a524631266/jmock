package com.zhangll.flink;

import com.zhangll.flink.random.*;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;

import java.lang.reflect.Field;


public class MockData<T> {
    /**
     * 默认没有rule，使用默认的rule
     * @param personClass
     * @return
     */
    public static Object mock(Class<?> personClass) {
       return mock(personClass, null);
    }

    /**
     *
     * @param personClass
     * @param rule
     * @return
     */
    public static Object mock(Class<?> personClass, Rule rule) {
        // 首先是否一定要有一个构造函数
        try {
            // 1. 先通过一个构造函数来获取数据
            Object o = personClass.newInstance();
            Field[] declaredFields = personClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();
                // 根据 生成的对象和类型来设置值
                assignRandom(o, declaredField, type ,rule);
            }
            return o;
        } catch ( InstantiationException e ) {
            throw new RuntimeException("初始化失败");
        } catch ( IllegalAccessException e ) {
            throw new RuntimeException("非法参数");
        }
    }

    private static void assignRandom(Object o, Field declaredField, Class<?> type, Rule rule) throws IllegalAccessException {
        if(BasicType.isInteger(type)){
            // TODO 规则
            declaredField.set(o, IntegerRandom.random());
        } else if( BasicType.isString(type)){
            declaredField.set(o, StringRandom.random());
        } else if(BasicType.isChar(type)){
            declaredField.set(o, CharRandom.random());
        } else if(BasicType.isDouble(type)){
            declaredField.set(o, DoubleRandom.random());
        } else if(BasicType.isFloat(type)){
            declaredField.set(o, FloatRandom.random());
        } else if(BasicType.isBoolean(type)){
            declaredField.set(o, BooleanRandom.random());
        }
//        else if(BasicType.isArray()){
//
//        }
    }

}
