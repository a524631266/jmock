package com.zhangll.flink;

import com.zhangll.flink.annotation.FieldTokenType;
import org.junit.Test;

import java.lang.reflect.Field;
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

    /**
     * getDeclaredFields
     */
    @Test
    public void test(){
        for (Field declaredField : Father.class.getDeclaredFields()) {
            FieldTokenType annotation = declaredField.getAnnotation(FieldTokenType.class);
            System.out.println(annotation);
        }
    }

}
