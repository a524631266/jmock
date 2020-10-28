package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RandomTest {
    private IntegerRandom integerRandom = new IntegerRandom();
    private LongRandom longRandom = new LongRandom();
    private DoubleRandom doubleRandom = new DoubleRandom();
    private BooleanRandom booleanRandom = new BooleanRandom();
    private StringRandom stringRandom = new StringRandom();
    private ListRandom listRandom = new ListRandom<>();
    @Before
    public void init(){

    }

    /**
     * 检测规则是否正确
     */
    @Test
    public void testIntegerRandom(){
        // 定义规则
        int min = 10;
        int max = 100;
        FieldToken token = new FieldToken(
                min,max,0,0,0,0,0, null,
                null
        );
        Rule rule = integerRandom.getRule(token);
        for (int i = 0; i < 100; i++) {
            int res1 = (int) rule.apply();
            assertTrue( res1 >= min && res1 < max);
        }
        int count = 5;

        FieldToken token2 = new FieldToken(min , max,count,0,0,0,0, null ,null);
        Rule rule2 = integerRandom.getRule(token2);
        for (int i = 0; i < 100; i++) {
            int res2 = (int) rule2.apply();
            assertTrue( res2 == count);
        }
    }
    /**
     * 检测规则是否正确
     *  奇葩的规则
     * 8 + (43391 / Math.pow(10,5)) 结果为8.433910000000001
     */
    @Test
    public void testDoubleRandom(){
        // 定义规则
        int min = 5;
        int max = 15;
        int count = 50;
        int dmin = 2;
        int dmax = 7;
        String value = "10.287";
        FieldToken token = new FieldToken(min,max,count,dmin,dmax,0,0, value,null);
        Rule rule = doubleRandom.getRule(token);
        for (int i = 0; i < 100; i++) {
            double res1 = (double) rule.apply();
//            System.out.println(res1);
            //
            assertTrue( res1 >= min && res1 < max + (Integer.valueOf(value.split("\\.")[1])+1));
            String s = String.valueOf(res1);
            int dLength = s.split("\\.")[1].length();
            assertTrue( dLength >= dmin && dLength <= dmax);
        }

        FieldToken token2 = new FieldToken(min,max,count,dmin,dmax,0,0, null,null);
        Rule rule2 = doubleRandom.getRule(token2);
        for (int i = 0; i < 100; i++) {
            double res1 = (double) rule2.apply();
            String s = String.valueOf(res1);
            int dLength = s.split("\\.")[1].length();
            int it = Integer.valueOf(s.split("\\.")[0]);

            assertTrue( dLength >= dmin && dLength <= dmax);
            assertTrue(it >= min && it <= max);
        }
    }

    /**
     * 测试成功
     */
    @Test
    public void testBooleanRandom(){
        // 定义规则
        int min = 1;
        int max = 15;
        int count = 0;
        String value = "false";
        FieldToken token = new FieldToken(min,max,count,0,0,0,0, value,null);
        Rule rule = booleanRandom.getRule(token);
        Map<Boolean, Integer>  map = new HashMap();
        for (int i = 0; i < 1000; i++) {
            boolean bool = (boolean) rule.apply();
            map.put(bool,map.getOrDefault(bool, 0)+1);
        }
        System.out.println("map:"+map);
    }
    /**
     * 检测规则是否正确
     */
    @Test
    public void testLongRandom(){
        // 定义规则
        int min = 10;
        int max = 100000;
        FieldToken token = new FieldToken(min,max,0,0,0,0,0, null,null);
        Rule rule = longRandom.getRule(token);
        for (int i = 0; i < 100; i++) {
            long res1 = (Long) rule.apply();
            assertTrue( res1 >= min && res1 < max);
        }
        int count = 600;

        FieldToken token2 = new FieldToken(min , max,count,0,0,0,0, null,null);
        Rule rule2 = longRandom.getRule(token2);
        for (int i = 0; i < 100; i++) {
            long res2 = (Long) rule2.apply();
            assertTrue( res2 == count);
        }
    }

    @Test
    public void testStringForNullValue(){
        // 定义规则
        int min = 1;
        int max = 20;
        String value = null;

        FieldToken token = new FieldToken(min,max,0,0,0,0,0, null,null);
        Rule rule = stringRandom.getRule(token);
        for (int i = 0; i < 10000; i++) {
            String object = (String) rule.apply();
            assertTrue( object.length() >= min && object.length() < max);
        }

    }
    @Test
    public void testStringForNullValueWithCount(){
        // 定义规则
        int min = 1;
        int max = 20;
        String value = null;
        int count = 5;
        FieldToken token = new FieldToken(min,max,count,0,0,0,0, null,null);
        Rule rule = stringRandom.getRule(token);
        for (int i = 0; i < 10000; i++) {
            String object = (String) rule.apply();
//            System.out.println(object);
            assertTrue( object.length() == count);
        }

    }

    @Test
    public void testStringForValue(){
        // 定义规则
        int min = 1;
        int max = 20;
        String value = "abc";
        FieldToken token = new FieldToken(min,max,0,0,0,0,0, value,null);
        Rule rule = stringRandom.getRule(token);
        for (int i = 0; i < 10000; i++) {
            String object = (String) rule.apply();
            assertTrue( object.length() >= value.length() * min && object.length() < max * value.length());
        }
    }

    @Test
    public void testStringForValuWithCount(){
        // 定义规则
        int min = 1;
        int max = 20;
        int count = 10;
        String value = "天是";
        FieldToken token = new FieldToken(min,max,count,0,0,0,0, value,null);
        Rule rule = stringRandom.getRule(token);
        for (int i = 0; i < 10000; i++) {
            String object = (String) rule.apply();
            System.out.println(object);
            assertTrue( object.length() == value.length() * count);
        }
    }

    @Test
    public void testStringForValuWithCountEmpty(){
        // 定义规则
        int min = 1;
        int max = 20;
        int count = 10;
        String value = "";
        FieldToken token = new FieldToken(min,max,count,0,0,0,0, value,null);
        Rule rule = stringRandom.getRule(token);
        for (int i = 0; i < 10000; i++) {
            String object = (String) rule.apply();
            System.out.println(object);
            assertTrue( object.length() == count);
        }
    }


    /**
     * 1. 'name|1': array
     * array 为空 或有数据
     * a) name|3: "" | null
     * b) name|2: ["asdf","asdf","ads"]
     */
    @Test
    public void testListRandomRule1(){
        // 定义规则
        int min = 0;
        int max = 0;
        int count = 10;
        String value = "";
        FieldToken token = new FieldToken(min,max,count,0,0,0,0, value,null);
        Rule rule = listRandom.getRule(token);
        for (int i = 0; i < 10000; i++) {
            String object = (String) rule.apply();
            System.out.println(object);
            assertTrue( object.length() == count);
        }
    }
}