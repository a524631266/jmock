package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class RandomTest {
    private IntegerRandom integerRandom = new IntegerRandom();
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
        FieldToken token = new FieldToken(min,max,0,0,0,0,0, null);
        Rule rule = integerRandom.getRule(token);
        for (int i = 0; i < 100; i++) {
            int res1 = (int) rule.apply();
            assertTrue( res1 >= min && res1 <= max);
        }
        int count = 5;

        FieldToken token2 = new FieldToken(min , max,count,0,0,0,0, null);
        Rule rule2 = integerRandom.getRule(token2);
        for (int i = 0; i < 100; i++) {
            int res2 = (int) rule2.apply();
            assertTrue( res2 == count);
        }
    }

}