package com.zhangll.flink.random;

import com.zhangll.flink.AnnotationMockContext;
import com.zhangll.flink.Father;
import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.parser.NodeParser;
import com.zhangll.flink.rule.Rule;
import org.junit.Before;
import org.junit.Test;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RandomTest {
    private IntegerSimpleRandomExecutor integerRandom = new IntegerSimpleRandomExecutor();
    private LongSimpleRandomExecutor longRandom = new LongSimpleRandomExecutor();
    private DoubleSimpleRandomExecutor doubleRandom = new DoubleSimpleRandomExecutor();
    private BooleanSimpleRandomExecutor booleanRandom = new BooleanSimpleRandomExecutor();
    private StringSimpleRandomExecutor stringRandom = new StringSimpleRandomExecutor();
    private ListRandomExecutor listRandom = new ListRandomExecutor<>();

    private Father father = new Father();
    private MockContext mockContext;

    private NodeParser nodeParser;
    @Before
    public void init(){
        mockContext = new AnnotationMockContext();
        nodeParser = new NodeParser();
    }

    /**
     * 检测规则是否正确
     */
    @Test
    public void testIntegerRandom(){
        // 定义规则
        int min = 10;
        int max = 100;
        int count = 5;
        Rule rule = integerRandom.getRule(
        new FieldToken.FieldTokenBuilder()
                .setMin(min)
                .setMax(max)
                .build()
        );
        for (int i = 0; i < 1000; i++) {
            int res1 = (int) rule.apply(mockContext, null);
            assertTrue( res1 >= min && res1 < max);
        }
        Rule rule2 = integerRandom.getRule(
                new FieldToken.FieldTokenBuilder()
                        .setMax(max)
                        .setMin(min)
                        .setCount(count)
                        .build()
        );
        for (int i = 0; i < 1000; i++) {
            int res2 = (int) rule2.apply(mockContext,null);
            assertTrue( res2 == count);
        }
    }
    @Test
    public void testDefaultIntegerRandom(){
        Rule rule = integerRandom.getRule();
        for (int i = 0; i < 10000; i++) {
            Integer value = (Integer)rule.apply(mockContext,null);
            assertTrue(value >= 1 && value < 1000);
        }
    }

    @Test
    public void testDefaultLongRandom(){
        Rule rule = longRandom.getRule();
        for (int i = 0; i < 10000; i++) {
            Long value = (Long)rule.apply(mockContext,null);
//            System.out.println(value);
            assertTrue(value >= 10 && value<1000);
        }
    }


    @Test
    public void testDefaultDoubleRandom(){
        Rule rule = doubleRandom.getRule();
        for (int i = 0; i < 10000; i++) {
            Double value = (Double)rule.apply(mockContext,null);
//            System.out.println(value);
//            assertTrue(value >= 10 && value<1000);
        }
    }
    @Test
    public void testDefaultBooleanRandom(){
        Map<Boolean , Integer> map = new HashMap<Boolean, Integer>();
        Rule rule = booleanRandom.getRule();
        for (int i = 0; i < 10000; i++) {
            Boolean value = (Boolean)rule.apply(mockContext,null);
            map.put(value,map.getOrDefault(value, 0)+1);
        }
//        System.out.println(map);
    }

    @Test
    public void testDefaultStringRandom(){
        Rule rule = stringRandom.getRule();
        for (int i = 0; i < 10000; i++) {
            String value = (String)rule.apply(mockContext,null);
//            System.out.println(value);
            assertTrue(value.length() >=1 && value.length() <3);
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
        FieldToken token = new FieldToken.FieldTokenBuilder()
                .setMin(min)
                .setMax(max)
                .setCount(count)
                .setDmin(dmin)
                .setDmax(dmax)
                .setValue(new String[]{value})
                .build();
        Rule rule = doubleRandom.getRule(token);
        for (int i = 0; i < 100; i++) {
            double res1 = (double) rule.apply(mockContext,null);
//            System.out.println(res1);
            //
            assertTrue( res1 >= min && res1 < max + (Integer.valueOf(value.split("\\.")[1])+1));
            String s = String.valueOf(res1);
            int dLength = s.split("\\.")[1].length();
            assertTrue( dLength >= dmin && dLength <= dmax);
        }

//        FieldToken token2 = new FieldToken(min,max,count,dmin,dmax,0,0, null,null);
        FieldToken token2 = new FieldToken.FieldTokenBuilder()
                .setMin(min)
                .setMax(max)
                .setCount(count)
                .setDmin(dmin)
                .setDmax(dmax)
                .build();
        Rule rule2 = doubleRandom.getRule(token2);
        for (int i = 0; i < 100; i++) {
            double res1 = (double) rule2.apply(mockContext,null);
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
        FieldToken token = new FieldToken.FieldTokenBuilder()
                .setMin(min)
                .setMax(max)
                .setCount(count)
                .setValue(new String[]{value})
                .build();
        Rule rule = booleanRandom.getRule(token);
        Map<Boolean, Integer>  map = new HashMap();
        for (int i = 0; i < 1000; i++) {
            boolean bool = (boolean) rule.apply(mockContext,null);
            map.put(bool,map.getOrDefault(bool, 0)+1);
        }
//        System.out.println("map:"+map);
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
            long res1 = (Long) rule.apply(mockContext,null);
            assertTrue( res1 >= min && res1 < max);
        }
        int count = 600;

        FieldToken token2 = new FieldToken(min , max,count,0,0,0,0, null,null);
        Rule rule2 = longRandom.getRule(token2);
        for (int i = 0; i < 100; i++) {
            long res2 = (Long) rule2.apply(mockContext,null);
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
        for (int i = 0; i < 200; i++) {
            String object = (String) rule.apply(mockContext,null);
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
        for (int i = 0; i < 200; i++) {
            String object = (String) rule.apply(mockContext,null);
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
        FieldToken token = new FieldToken.FieldTokenBuilder()
                .setMin(min)
                .setMax(max)
                .setValue(new String[]{value})
                .build();
        Rule rule = stringRandom.getRule(token);
        for (int i = 0; i < 200; i++) {
            String object = (String) rule.apply(mockContext,null);
            assertTrue( object.length() >= value.length() * min && object.length() < max * value.length());
        }
    }

    @Test
    public void testStringForValuWithCount(){
        // 定义规则
        int min = 1;
        int max = 20;
        int count = 1;
        String value = "天是";
        String value2 = "天是2";
        FieldToken token = new FieldToken.FieldTokenBuilder()
                .setMin(min)
                .setMax(max)
                .setCount(count)
                .setValue(new String[]{value, value2})
                .build();
        Rule rule = stringRandom.getRule(token);
        for (int i = 0; i < 200; i++) {
            String object = (String) rule.apply(mockContext,null);
//            System.out.println(object);
            assertTrue( object.length() == value.length() * count
                    || object.length() == value2.length() * count
            );
        }
    }

    @Test
    public void testStringForValuWithCountEmpty(){
        // 定义规则
        int min = 1;
        int max = 20;
        int count = 10;
        String value = "";
        FieldToken token = new FieldToken.FieldTokenBuilder()
                .setMin(min)
                .setMax(max)
                .setCount(count)
                .setValue(new String[]{value})
                .build();
        Rule rule = stringRandom.getRule(token);
        for (int i = 0; i < 200; i++) {
            String object = (String) rule.apply(mockContext,null);
//            System.out.println(object);
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
    public void testListRandomRule1() throws NoSuchFieldException {
        // 定义规则
        int min = 0;
        int max = 0;
        int count = 10;
        String value = "";
        ListRandomExecutor.DefaultListRule rule = (ListRandomExecutor.DefaultListRule)listRandom.getRule(
                new FieldToken.FieldTokenBuilder()
                .setMin(min)
                .setMax(max)
                .setCount(count)
                .setValue(new String[]{value}).build()
        );
        Field sonsNameList = Father.class.getDeclaredField("sonsNameList");
        for (int i = 0; i < 200; i++) {
            List object = (List) rule.apply(mockContext, nodeParser.initNodeTree(sonsNameList.getType(), sonsNameList));
//            System.out.println(object);
            assertTrue( object.size() == count);
        }
    }

    /**
     * 默认为10
     * @throws NoSuchFieldException
     */
    @Test
    public void testDefalutListRandomRuleForInteger() throws NoSuchFieldException {
        ListRandomExecutor.DefaultListRule rule = (ListRandomExecutor.DefaultListRule)listRandom.getRule();
        int count = 10;
        Field field = Father.class.getDeclaredField("sonsAgeList");

        for (int i = 0; i < 200; i++) {
            List object = (List) rule.apply(mockContext, nodeParser.initNodeTree(field.getType(), field));
//            System.out.println(object);
            assertTrue( object.size() == count);
        }
    }


    /**
     * 默认为10
     * @throws NoSuchFieldException
     */
    @Test
    public void testDefalutListRandomRuleForString() throws NoSuchFieldException {
        ListRandomExecutor.DefaultListRule rule = (ListRandomExecutor.DefaultListRule)listRandom.getRule();
        int count = 10;
        Field field = Father.class.getDeclaredField("sonsNameList");
        for (int i = 0; i < 200; i++) {
            List object = (List) rule.apply(mockContext, nodeParser.initNodeTree(field.getType(), field));
//            System.out.println(object);
            assertTrue( object.size() == count);
        }
    }

    /**
     * 默认为10
     * @throws NoSuchFieldException
     */
    @Test
    public void testDefalutListRandomRuleForDouble() throws NoSuchFieldException {
        ListRandomExecutor.DefaultListRule rule = (ListRandomExecutor.DefaultListRule)listRandom.getRule();
        int count = 10;
        Field field = Father.class.getDeclaredField("sonsMoneyList");
        for (int i = 0; i < 200; i++) {
            List object = (List) rule.apply(mockContext, nodeParser.initNodeTree(field.getType(), field));
//            System.out.println(object);
            assertTrue( object.size() == count);
        }
    }

    /**
     * 默认为10
     * @throws NoSuchFieldException
     */
    @Test
    public void testDefalutListRandomRuleForLong() throws NoSuchFieldException {
        ListRandomExecutor.DefaultListRule rule = (ListRandomExecutor.DefaultListRule)listRandom.getRule();
        int count = 10;
        Field field = Father.class.getDeclaredField("sonsLongList");
        for (int i = 0; i < 200; i++) {
            List object = (List) rule.apply(mockContext, nodeParser.initNodeTree(field.getType(), field));
//            System.out.println(object);
            assertTrue( object.size() == count);
        }
    }
    /**
     * 默认为10
     * @throws NoSuchFieldException
     */
    @Test
    public void testDefalutListRandomRuleForStringByValue() throws NoSuchFieldException {
        ListRandomExecutor.DefaultListRule rule = (ListRandomExecutor.DefaultListRule)listRandom.getRule(
                new FieldToken.FieldTokenBuilder()
                        .setCount(10)
                        .setValue(new String[]{""})
                        .setSubFieldToken(
                                new FieldToken.FieldTokenBuilder()
                                        .setMin(1)
                                        .setMax(5)
                                        .setDmin(0)
                                        .setDmax(5)
                                        .build()
                        )
                        .build()
        );
        int count = 10;
        Field field = Father.class.getDeclaredField("sonsMoneyList");
        for (int i = 0; i < 200; i++) {
            List object = (List) rule.apply(mockContext, nodeParser.initNodeTree(field.getType(), field));
//            System.out.println(object);
            assertTrue( object.size() == count);
        }
    }
}