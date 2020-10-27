package com.zhangll.flink.random;

import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.Random;

public class CharRandom implements RandomType{
    // 返回 char类型数值
    // char大小可以
    public static char random() {
        // 两个字节 0- 65535
        char random = (char) new Random().nextInt(65535);
        return random;
    }

    /**
     * 中文规则 19968 40869
     * @return
     */
    public static char randomChinese() {
        // 两个字节 0- 65535
        char random = (char) (new Random().nextInt(45567) + 19968);
        return random;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == char.class || type == Character.class;
    }

    @Override
    public void updateField(Object o, Field declaredField, Rule rule) throws IllegalAccessException {
        declaredField.set(o, CharRandom.random());
    }

    @Override
    public Rule getRule() {
        // TODO
        return null;
    }
}
