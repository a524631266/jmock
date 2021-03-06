package com.zhangll.jmock.core.random;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.Father;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class IntStepTest {

    private AnnotationMockContext context;
    @Before
    public void init(){
        context = new AnnotationMockContext();
    }

//    @Test
//    public void testMin3Max100Step20(){
//        int min = 3;
//        int max = 100;
//        int step = 20;
//        int process = 0;
//        int gap = max - min + 1;
//        for (int i = 0; i < 100; i++) {
//            System.out.println("Father.class: "+ Father.class);
//            System.out.println(context);
//            Father mock = (Father) context.mock(Father.class);
//            int min3Max100 = mock.getMin3Max100();
////            System.out.println(min3Max100);
//            process = step * i;
//            final int fi = min + process % gap;
//            assertEquals(fi, min3Max100);
//        }
//    }
//
//    @Test
//    public void testMin3Max0Step20(){
//        int min = 3;
//        int max = 100;
//        int step = -20;
//        int process = 0;
//        int gap = max - min + 1;
//        for (int i = 0; i < 100; i++) {
//            Father mock = (Father) context.mock(Father.class);
//            int min3Max100 = mock.getMin3Max100ReduceStep();
////            System.out.println(min3Max100);
//            process = step * i ;
//            final int fi = max + process % gap;
//            assertEquals(fi, min3Max100);
//        }
//    }
//
//    /**
//     * value = {"1", "2", "8", "4", "20"}
//     */
//    @Test
//    public void testIntValuePlus(){
//        String[] value = {"1", "2", "8", "4", "20"};
//        int length = value.length;
//        int process = 0;
//        int step = 2;
//        for (int i = 0; i < 100; i++) {
//            Father mock = (Father) context.mock(Father.class);
//            int intValue = mock.getIntValue();
////            System.out.println(intValue);
//            process = step * i ;
//            assertEquals(Integer.parseInt(value[process % length]), intValue);
//        }
//    }
//    /**
//     * value = {"1", "2", "8", "4", "20"}
//     */
//    @Test
//    public void testIntValueMinus(){
//        String[] value = {"1", "2", "8", "4", "20"};
//        int length = value.length;
//        int process = 0;
//        int step = -3;
//        for (int i = 0; i < 100; i++) {
//            Father mock = (Father) context.mock(Father.class);
//            int intValue = mock.getIntValueMinus();
////            System.out.println(intValue);
//            process = step * i ;
//            int gap = process % length;
//            assertEquals(Integer.parseInt(value[length-1+ gap]), intValue);
//        }
//    }

    @Test
    public void test11(){
        PP mock = new AnnotationMockContext().mock(PP.class);
        System.out.println(mock);
    }
}
