package com.zhangll.flink.random;

import java.util.Random;

public class FloatRandom {
    // 返回 char类型数值
    public static float random() {
        float v = new Random().nextFloat();
        return v;
    }
}
