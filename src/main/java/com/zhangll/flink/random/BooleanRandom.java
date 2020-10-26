package com.zhangll.flink.random;

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
}
