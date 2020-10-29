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
    public void testAnnotation()
    {
        Father father = (Father) new AnnotationMockContext().mock(Father.class);
        System.out.println(father);
        assertTrue(father.getAge() >=10 && father.getAge() < 100);

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
