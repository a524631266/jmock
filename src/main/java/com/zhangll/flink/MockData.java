package com.zhangll.flink;

import com.zhangll.flink.random.*;
import com.zhangll.flink.type.BasicType;

import java.lang.reflect.Field;


public class MockData<T> {

    public static Object mock(Class<?> personClass) {
        // 首先是否一定要有一个构造函数
        try {
            // 1. 先通过一个构造函数来获取数据
            Object o = personClass.newInstance();

            Field[] declaredFields = personClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();
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
            }
            return o;
        } catch ( InstantiationException e ) {
            throw new RuntimeException("初始化失败");
        } catch ( IllegalAccessException e ) {
            throw new RuntimeException("非法参数");
        }
    }

}
