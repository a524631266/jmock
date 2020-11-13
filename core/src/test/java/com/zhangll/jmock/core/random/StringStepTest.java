package com.zhangll.jmock.core.random;

import com.zhangll.flink.AnnotationMockContext;
import com.zhangll.flink.annotation.BasicTokenInfo;
import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Getter;
import lombok.ToString;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Getter
@ToString
class StringFather {
    @BasicTokenInfo( step = "2", value ={"爱我中华", "hello", "s", "easd", "帅"})
    private String string1;
    @BasicTokenInfo(step = "-3", value = {"爱我中华", "hello", "s", "easd", "帅"})
    private String string2;
}


public class StringStepTest {

    /**
     * value = {"爱我中华", "hello", "s", "easd", "帅"}
     */
    @Test
    public void testIntValuePlus(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"爱我中华", "hello", "s", "easd", "帅"};
        int length = value.length;
        int process = 0;
        int step = 2;
        for (int i = 0; i < 100; i++) {
            StringFather mock = (StringFather)context.mock(StringFather.class);
            String element = mock.getString1();

//            System.out.println(element);
            process = step * i ;
            assertEquals(value[process % length], element);
        }
    }
    /**
     * value = {"爱我中华", "hello", "s", "easd", "帅"}
     */
    @Test
    public void testIntValueMinus(){
        AnnotationMockContext context = new AnnotationMockContext();
        String[] value = {"爱我中华", "hello", "s", "easd", "帅"};
        int length = value.length;
        int process = 0;
        int step = -3;
        for (int i = 0; i < 100; i++) {
            StringFather mock = (StringFather)context.mock(StringFather.class);
            String element = mock.getString2();
            process = step * i ;
            int gap = process % length;
            assertEquals(value[length-1+ gap] ,element);
        }
    }

}
