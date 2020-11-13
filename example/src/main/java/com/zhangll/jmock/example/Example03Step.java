package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.ToString;

@ToString
class Step01 {
    @BasicTokenInfo(min = "5", max = "10", step = "2")
    private int increase;

    @BasicTokenInfo(min = "5", max = "10", step = "2")
    private double increase2;
}

public class Example03Step {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(Step01.class);
            System.out.println(mock);
        }

    }
}
