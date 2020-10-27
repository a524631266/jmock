package com.zhangll.flink.uitl;

import java.util.Random;

public class RandomUtil {
    /**
     * [min, max)
     * @param min
     * @param max
     * @return
     */
    public static Integer getMin2Max(int min, int max){
        int gap = max - min;
        return new Random().nextInt(gap) + min;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Integer min2Max = getMin2Max(0, 10);
            System.out.println(min2Max);
        }

    }
}
