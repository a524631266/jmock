package com.zhangll.flink.random;

import com.zhangll.flink.AnnotationMockContext;
import com.zhangll.flink.Father;
import com.zhangll.flink.Son;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepTest {
    @Test
    public void testMin3Max100Step20(){
        AnnotationMockContext context = new AnnotationMockContext();
        int min = 3;
        int max = 100;
        int step = 20;
        int process = 0;
        int gap = max - min + 1;
        for (int i = 0; i < 100; i++) {
            Father mock = (Father)context.mock(Father.class);
            int min3Max100 = mock.getMin3Max100();
//            System.out.println(min3Max100);
            process = step * i ;
            final int fi = min + process % gap;
            assertEquals(fi, min3Max100);
        }
    }

    @Test
    public void testMin3Max0Step20(){
        AnnotationMockContext context = new AnnotationMockContext();
        int min = 3;
        int max = 100;
        int step = -20;
        int process = 0;
        int gap = max - min + 1;
        for (int i = 0; i < 100; i++) {
            Father mock = (Father)context.mock(Father.class);
            int min3Max100 = mock.getMin3Max100ReduceStep();
//            System.out.println(min3Max100);
            process = step * i ;
            final int fi = max + process % gap;
            assertEquals(fi, min3Max100);
        }
    }

    /**
     * value = {"1", "2", "8", "4", "20"}
     */
    @Test
    public void testIntValuePlus(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"1", "2", "8", "4", "20"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            Father mock = (Father)context.mock(Father.class);
            int intValue = mock.getIntValue();
//            System.out.println(intValue);
            process = step * i ;
            assertEquals(Integer.parseInt(value[process % length]), intValue);
        }
    }
    /**
     * value = {"1", "2", "8", "4", "20"}
     */
    @Test
    public void testIntValueMinus(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"1", "2", "8", "4", "20"};
        int length = value.length;
        int process = 0;
        int step = -3;
        for (int i = 0; i < 100; i++) {
            Father mock = (Father)context.mock(Father.class);
            int intValue = mock.getIntValueMinus();
//            System.out.println(intValue);
            process = step * i ;
            int gap = process % length;
            assertEquals(Integer.parseInt(value[length-1+ gap]), intValue);
        }
    }
}
