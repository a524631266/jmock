package com.zhangll.flink.random;

import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.Random;

public class FloatRandom implements RandomType{
    // 返回 char类型数值
    public static float random() {
        float v = new Random().nextFloat();
        return v;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Float.class || type == float.class;
    }

    @Override
    public void updateField(Object o, Field declaredField, Rule rule) throws IllegalAccessException {
        declaredField.set(o, FloatRandom.random());
    }
}
