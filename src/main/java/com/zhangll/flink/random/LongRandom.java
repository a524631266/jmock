package com.zhangll.flink.random;

import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.Random;

public class LongRandom implements RandomType{

    public static long random(){
        int i = new Random().nextInt(100);
//        System.out.println(i);
        return i;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Long.class || type == long.class;
    }

    @Override
    public void updateField(Object o, Field declaredField, Rule rule) throws IllegalAccessException {
        declaredField.set(o, LongRandom.random());
    }
}
