package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.Random;

public class ShortRandom implements RandomType{

    public static short random(){
        short i = (short) new Random().nextInt(100);
//        System.out.println(i);
        return i;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Short.class || type == short.class;
    }

    @Override
    public void updateField(Object o, Field declaredField, Rule rule) throws IllegalAccessException {
        declaredField.set(o, ShortRandom.random());
    }

    @Override
    public Rule getRule() {
        // TODO
        return null;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        // TODO
        return null;
    }
}
