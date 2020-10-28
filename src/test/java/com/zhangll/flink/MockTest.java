package com.zhangll.flink;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class MockTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
        Father father = (Father) MockContext.mock(Father.class);
        System.out.println(father);
    }

    @Test
    public void test(){
        Set<String> set = new HashSet();
    }

}
