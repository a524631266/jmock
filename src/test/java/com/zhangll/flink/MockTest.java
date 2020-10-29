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
        int count = 0;
        while ((count ++) < 10000){
            Father father = (Father) new AnnotationMockContext().mock(Father.class);
//            System.out.println(father);
            assertTrue(father.getAge() >=10 && father.getAge() < 100);
        }
    }


    /**
     * Rigorous Test :-)
     */
    @Test
    public void testAsyncAnnotation() throws InterruptedException {
        long l = System.currentTimeMillis();
        int count = 0;
        while ((count ++) < 10000){
            new Thread(){
                @Override
                public void run() {
                    Father father = (Father) new AnnotationMockContext().mock(Father.class);
//                    System.out.println(father);
                    assertTrue(father.getAge() >= 10 && father.getAge() < 100);
                    System.out.println("count:" + (System.currentTimeMillis() - l));
                }
            }.start();
        }
        System.out.println("end");
        Thread.sleep(5000);
    }
    /**
     * getDeclaredFields
     */
    @Test
    public void test(){
        for (Field declaredField : Father.class.getDeclaredFields()) {
            FieldTokenType annotation = declaredField.getAnnotation(FieldTokenType.class);
//            System.out.println(annotation);
        }
    }



    @Test
    public void testNestModel()
    {
        int count = 0;
        while ((count ++) < 100){
            Father father = (Father) new AnnotationMockContext().mock(Father.class);
            System.out.println(father);
            assertTrue(father.getAge() >=10 && father.getAge() < 100);
        }
    }
}
