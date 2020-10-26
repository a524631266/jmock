package com.zhangll.flink.random;

import java.util.Random;

public class BooleanRandom {
    // 返回 char类型数值
    public static boolean random() {
        boolean v = new Random().nextBoolean();
        return v;
    }


}
