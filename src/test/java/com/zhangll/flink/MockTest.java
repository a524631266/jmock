package com.zhangll.flink;

import com.zhangll.flink.annotation.BasicTokenInfo;
import com.zhangll.flink.enumt.RegrexDemo;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
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
        while ((count ++) < 200){
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
        while ((count ++) < 200){
            new Thread(){
                @Override
                public void run() {
                    Father father = (Father) new AnnotationMockContext().mock(Father.class);
//                    System.out.println(father);
                    assertTrue(father.getAge() >= 10 && father.getAge() < 100);
//                    System.out.println("count:" + (System.currentTimeMillis() - l));
                }
            }.start();
        }
    }

    @Test
    public void testNestModel()
    {
        int count = 0;
        while ((count ++) < 1){
            Father father = (Father) new AnnotationMockContext().mock(Father.class);
//            System.out.println(father);
            assertTrue(father.getAge() >=10 && father.getAge() < 100);
        }
    }

    @Test
    public void testNestModel2()
    {
        AnnotationMockContext context = new AnnotationMockContext();
        for (int i = 0; i < 1; i++) {
            Father mock = (Father)context.mock(Father.class);
//            System.out.println(mock.getRegrex());
//            System.out.println(mock);
            assertTrue(mock != null);
        }
    }
    @Test
    public void testListStep()
    {
        AnnotationMockContext context = new AnnotationMockContext();
        String arrayValueRegrex = "\\d{5,6}\\w+\\d";
        Pattern compile = Pattern.compile(arrayValueRegrex);
        for (int i = 0; i < 10; i++) {
            Father mock = (Father)context.mock(Father.class);
//            System.out.println(mock.getSonsNameList());
            assertEquals(10,mock.getSonsNameList().size());
            assertEquals("张三",mock.getSonsNameList().get(0));
            assertTrue(compile.matcher(mock.getSonsNameList().get(1)).find());
            assertEquals("王五",mock.getSonsNameList().get(3));
            assertEquals("李四",mock.getSonsNameList().get(4));
        }
    }

    @Test
    public void testArrayStep()
    {
        AnnotationMockContext context = new AnnotationMockContext();
        String arrayValueRegrex = "\\d{1,3}  abcd\\/ \\d";
        Pattern compile = Pattern.compile(arrayValueRegrex);
        for (int i = 0; i < 10; i++) {
            Father mock = (Father)context.mock(Father.class);
//            System.out.println(Arrays.stream(mock.getStringArr())
//                    .map(ele -> ele + "],[")
//                    .collect(Collectors.joining()));

            assertEquals(10,mock.getStringArr().length);
            assertEquals("张三",mock.getStringArr()[0]);
            assertTrue(compile.matcher(mock.getStringArr()[1]).find());
            assertEquals("王五",mock.getStringArr()[3]);
            assertEquals("李四",mock.getStringArr()[4]);
        }
    }

}
