package com.zhangll.jmock.core.uitl;


import com.zhangll.jmock.core.Father;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class KeyUtilTest {

    @Test
    public void generateKey() {
        Father father = new Father();
        Class<? extends Father> aClass = father.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
//            System.out.println(KeyUtil.generateKey(aClass, declaredField));
        }
    }
}