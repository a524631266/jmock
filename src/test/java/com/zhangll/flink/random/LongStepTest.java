package com.zhangll.flink.random;

import com.zhangll.flink.AnnotationMockContext;
import com.zhangll.flink.annotation.BasicTokenInfo;
import lombok.Getter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Getter
class LongFather {

    @BasicTokenInfo(min = "3", max = "100", step = "20")
    private long i;
    @BasicTokenInfo(min = "3", max = "100", step = "-20")
    private long i2;
    @BasicTokenInfo(min = "3", max = "100", step = "2", value = {"1", "2", "8", "4", "20"})
    private long i3;
    @BasicTokenInfo(min = "3", max = "100", step = "-3", value = {"1", "2", "8", "4", "20"})
    private long i4;
}


public class LongStepTest {
    @Test
    public void testMin3Max100Step20(){
        AnnotationMockContext context = new AnnotationMockContext();
        int min = 3;
        int max = 100;
        int step = 20;
        int process = 0;
        int gap = max - min + 1;
        for (int i = 0; i < 100; i++) {
            LongFather mock = (LongFather)context.mock(LongFather.class);
            long min3Max100 = mock.getI();
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
            LongFather mock = (LongFather)context.mock(LongFather.class);
            long min3Max100 = mock.getI2();
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
            LongFather mock = (LongFather)context.mock(LongFather.class);
            long intValue = mock.getI3();
//            System.out.println(intValue);
            process = step * i ;
            assertEquals(Long.parseLong(value[process % length]), intValue);
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
            LongFather mock = (LongFather)context.mock(LongFather.class);
            long intValue = mock.getI4();
//            System.out.println(intValue);
            process = step * i ;
            int gap = process % length;
            assertEquals(Long.parseLong(value[length-1+ gap]), intValue);
        }
    }
}
