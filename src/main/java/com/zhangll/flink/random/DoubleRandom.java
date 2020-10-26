package com.zhangll.flink.random;

import java.util.Random;

public class DoubleRandom implements RandomType{
    // 返回 char类型数值
    public static double random() {
        double v = new Random().nextDouble();
        return v;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Double.class || type == double.class;
    }
}
