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

    /**
     * 等概率事件
     *  min/ (min + max)
     * @param min
     * @param max
     * @return
     */
    public static Boolean getBoolean(int min, int max){
        double v = (min * 1.0) / (min + max);
        double v1 = new Random().nextDouble();
        if(v1 >= v){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            Integer min2Max = getMin2Max(0, 10);
            System.out.println(min2Max);
        }

    }
}
