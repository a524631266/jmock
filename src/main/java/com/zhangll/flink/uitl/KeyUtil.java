package com.zhangll.flink.uitl;

import java.lang.reflect.Field;

public class KeyUtil {
    /**
     * 返回一个key，用户来存储在store中，作为唯一标志
     *
     *
     * @param cls Father
     * @param field 其中一个
     * @return com.zhangll.flink.Father#sonsNameList1
     */
    public static String generateKey(Class cls, Field field){
        return cls.getName() +"#"+ field.getName();
    }
}
