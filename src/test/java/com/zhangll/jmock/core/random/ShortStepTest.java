package com.zhangll.jmock.core.random;

import com.zhangll.flink.AnnotationMockContext;
import com.zhangll.flink.Father;
import com.zhangll.flink.annotation.BasicTokenInfo;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Getter
class ShortFather {

    @BasicTokenInfo(min = "3", max = "100", step = "20")
    private short i;
    @BasicTokenInfo(min = "3", max = "100", step = "-20")
    private Short i2;
    @BasicTokenInfo(min = "3", max = "100", step = "2", value = {"1", "2", "8", "4", "20"})
    private Short i3;
    @BasicTokenInfo(min = "3", max = "100", step = "-3", value = {"1", "2", "8", "4", "20"})
    private Short i4;
}



public class ShortStepTest {
    @Test
    public void testMin3Max100Step20(){
        AnnotationMockContext context = new AnnotationMockContext();
        int min = 3;
        int max = 100;
        int step = 20;
        int process = 0;
        int gap = max - min + 1;
        for (int i = 0; i < 100; i++) {
            ShortFather mock = (ShortFather)context.mock(ShortFather.class);
            short min3Max100 = mock.getI();
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
            ShortFather mock = (ShortFather)context.mock(ShortFather.class);
            int min3Max100 = mock.getI2();
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
            ShortFather mock = (ShortFather)context.mock(ShortFather.class);
            int intValue = mock.getI3();
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
            ShortFather mock = (ShortFather)context.mock(ShortFather.class);
            int intValue = mock.getI4();
//            System.out.println(intValue);
            process = step * i ;
            int gap = process % length;
            assertEquals(Integer.parseInt(value[length-1+ gap]), intValue);
        }
    }
}
