package com.zhangll.flink.random;

import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.Random;

public class BooleanRandom implements RandomType{

    // 返回 char类型数值
    public static boolean random() {
        boolean v = new Random().nextBoolean();
        return v;
    }


    @Override
    public boolean isCurrentType(Class type) {
        return type == Boolean.class || type == boolean.class;
    }


    @Override
    public void updateField(Object o, Field declaredField, Rule rule) throws IllegalAccessException {
        declaredField.set(o, BooleanRandom.random());
    }

    @Override
    public Rule getRule() {
        //TODO
        return null;
    }
}
