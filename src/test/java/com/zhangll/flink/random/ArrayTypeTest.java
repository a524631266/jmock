package com.zhangll.flink.random;

import com.zhangll.flink.Father;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ArrayTypeTest {
    @Test
    public void testStrArray(){
        String[] strArray = {"12", "234"};
        Class<?> componentType = strArray.getClass().getComponentType();
        System.out.println(componentType);
        Object o = Array.newInstance(componentType, 5);
        System.out.println(o);
    }
    @Test
    public void testIntArray(){
        int[] a = {1, 3};
        Class<?> componentType = a.getClass().getComponentType();
        System.out.println(componentType);
        Object o = Array.newInstance(componentType, 5);
        System.out.println(o);
    }
    @Test
    public void testIntegArray(){
        Integer[] a = {1, 3};
        Class<?> componentType = a.getClass().getComponentType();
        System.out.println(componentType);
        Object o = Array.newInstance(componentType, 5);
        System.out.println(o);
    }

    /**
     * c测试发现 getComponentType 不为null代表数组
     */
    @Test
    public void testListArray(){
        List<String> a = new ArrayList();
        a.add("123");
        Class<?> componentType = a.getClass().getComponentType();
        System.out.println(componentType);
        assertTrue(componentType == null);
    }

    /**
     * 测试发现 getComponentType 不为null代表数组
     */
    @Test
    public void testWrappingTransfer(){
        // 1. 基本类型元组可以赋值。但是不能修改长度。
//        Object doubles =Array.newInstance(double.class, 10);
//
//        doubles[0] = 3.8;
//        System.out.println(doubles);
        Double[] doubles = {1.112,23.3,2424.02};
        double[] doubles2 = {1.112,23.3,2424.02};

    }

    /**
     * 如何区分基本数据类型和object
     */
    @Test
    public void testBasicObject(){
        Class<Double> a = double.class;
        Class<Double> b = Double.class;
        Father father = new Father();
        Class<? extends Father> aClass = father.getClass();
         // a 是基本类型
        assertTrue(a.isPrimitive());
        assertTrue(!b.isPrimitive());
        assertTrue(!aClass.isPrimitive());
    }


}
