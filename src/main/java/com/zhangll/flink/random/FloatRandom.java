package com.zhangll.flink.random;

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
}
