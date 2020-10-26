package com.zhangll.flink.random;

import java.util.Random;

public class LongRandom implements RandomType{

    public static long random(){
        int i = new Random().nextInt(100);
        System.out.println(i);
        return i;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Long.class || type == long.class;
    }
}
