package com.zhangll.jmock.core.random;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Getter
@ToString
class CharFather {

    @BasicTokenInfo(min = "3", max = "100", step = "20")
    private char i;
    @BasicTokenInfo(min = "3", max = "103", step = "-20")
    private Character i2;
    @BasicTokenInfo( step = "2", value ={"a", "b", "c", "e", "f"})
    private char i3;
    @BasicTokenInfo(step = "-3", value = {"a", "b", "c", "e", "f"})
    private Character i4;
}


public class CharStepTest {
    @Test
    public void testMin3Max100Step20(){
        AnnotationMockContext context = new AnnotationMockContext();
        int min = 3;
        int max = 100;
        int step = 20;
        int process = 0;
        int gap = max - min + 1;
        for (int i = 0; i < 100; i++) {
            CharFather mock = (CharFather)context.mock(CharFather.class);
            char min3Max100 = mock.getI();
//            System.out.println(min3Max100);
            process = step * i ;
            final int fi = min + process % gap;
            assertTrue((char)fi == min3Max100);
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
            CharFather mock = (CharFather)context.mock(CharFather.class);
            char min3Max100 = mock.getI2();
            assertTrue(before == min3Max100);
            before += step;
            if(before < min){
                before = max + 1 - (min - before);
            }
        }
    }

    /**
     * value = {"a", "b", "c", "e", "f"}
     */
    @Test
    public void testIntValuePlus(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"a", "b", "c", "e", "f"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            CharFather mock = (CharFather)context.mock(CharFather.class);
            char doubleValue = mock.getI3();
//            System.out.println(doubleValue);
            process = step * i ;
            assertTrue(value[process % length].toCharArray()[0] == doubleValue);
        }
    }
    /**
     * value = {"a", "b", "c", "e", "f"}
     */
    @Test
    public void testIntValueMinus(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"a", "b", "c", "e", "f"};
        int length = value.length;
        int process = 0;
        int step = -3;
        for (int i = 0; i < 100; i++) {
            CharFather mock = (CharFather)context.mock(CharFather.class);
            char doubleValue = mock.getI4();
            process = step * i ;
            int gap = process % length;
            assertTrue(value[length-1+ gap].toCharArray()[0] == doubleValue);
        }
    }

    @Test
    public void testTransfer(){
        String word = "a";
        System.out.println(word.toCharArray());

    }
}
