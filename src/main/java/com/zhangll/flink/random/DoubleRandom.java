package com.zhangll.flink.random;

import java.util.Random;

public class DoubleRandom {
    // 返回 char类型数值
    public static double random() {
        double v = new Random().nextDouble();
        return v;
    }
}
