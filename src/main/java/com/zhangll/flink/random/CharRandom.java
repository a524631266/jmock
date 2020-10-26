package com.zhangll.flink.random;

import java.util.Random;

public class CharRandom {
    // 返回 char类型数值
    // char大小可以
    public static char random() {
        // 两个字节 0- 65535
        char random = (char) new Random().nextInt(65535);
        return random;
    }

    /**
     * 中文规则 19968 40869
     * @return
     */
    public static char randomChinese() {
        // 两个字节 0- 65535
        char random = (char) (new Random().nextInt(45567) + 19968);
        return random;
    }
}
