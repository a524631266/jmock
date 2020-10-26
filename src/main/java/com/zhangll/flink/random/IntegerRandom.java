package com.zhangll.flink.random;

import java.util.Random;

public class IntegerRandom {

    public static int random(){
        int i = new Random().nextInt(100);
        System.out.println(i);
        return i;
    }
}
