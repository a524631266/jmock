package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.ToString;

@ToString
class IntegerPojo{
    @BasicTokenInfo(min = "1000", max = "10000")
    private Integer int1;

    @BasicTokenInfo(value = {"1" , "10", "100"})
    private int int2;

}

public class IntegerExample {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(IntegerPojo.class);
            System.out.println(mock);
        }
    }
}
