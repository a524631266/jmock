package com.zhangll.flink.random;

import com.zhangll.flink.AnnotationMockContext;
import com.zhangll.flink.annotation.BasicTokenInfo;
import lombok.Getter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Getter
class DoubleFather {

    @BasicTokenInfo(min = "3", max = "100", dmin = "2", dmax = "5", step = "20")
    private double i;
    @BasicTokenInfo(min = "3", max = "103", step = "-20")
    private Double i2;
    @BasicTokenInfo( step = "2", value ={"1.2", "2.5", "8.8", "4.4", "20.5"})
    private double i3;
    @BasicTokenInfo(step = "-3", value = {"1.2", "2.5", "8.8", "4.4", "20.5"})
    private Double i4;
}


public class DoubleStepTest {
    @Test
    public void testMin3Max100Step20(){
        AnnotationMockContext context = new AnnotationMockContext();
        int min = 3;
        int max = 100;
        int step = 20;
        int process = 0;
        int gap = max - min + 1;
        for (int i = 0; i < 100; i++) {
            DoubleFather mock = (DoubleFather)context.mock(DoubleFather.class);
            double min3Max100 = mock.getI();
            System.out.println(min3Max100);
            process = step * i ;
            final double fi = (min + process % gap) * 1.0;
            assertTrue(fi == min3Max100);
        }
    }

    @Test
    public void testMin3Max0Step20(){
        AnnotationMockContext context = new AnnotationMockContext();
        int min = 3;
        int max = 103;
        int step = -20;
        double before = max;
        for (int i = 0; i < 100; i++) {
            DoubleFather mock = (DoubleFather)context.mock(DoubleFather.class);
            double min3Max100 = mock.getI2();
            System.out.println(min3Max100);
            assertTrue(before == min3Max100);
            before += step;
            if(before < min){
                before = max + 1 - (min - before);
            }
        }
    }

    /**
     * value = {"1.2", "2.5", "8.8", "4.4", "20.5"}
     */
    @Test
    public void testIntValuePlus(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"1.2", "2.5", "8.8", "4.4", "20.5"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            DoubleFather mock = (DoubleFather)context.mock(DoubleFather.class);
            double doubleValue = mock.getI3();
//            System.out.println(doubleValue);
            process = step * i ;
            assertTrue(Double.parseDouble(value[process % length]) == doubleValue);
        }
    }
    /**
     * value = {"1.2", "2.5", "8.8", "4.4", "20.5"}
     */
    @Test
    public void testIntValueMinus(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"1.2", "2.5", "8.8", "4.4", "20.5"};
        int length = value.length;
        int process = 0;
        int step = -3;
        for (int i = 0; i < 100; i++) {
            DoubleFather mock = (DoubleFather)context.mock(DoubleFather.class);
            double doubleValue = mock.getI4();
//            System.out.println(intValue);
            process = step * i ;
            int gap = process % length;
            assertTrue(Double.parseDouble(value[length-1+ gap]) == doubleValue);
        }
    }
}
