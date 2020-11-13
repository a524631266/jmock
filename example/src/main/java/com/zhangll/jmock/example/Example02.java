package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.ToString;

@ToString
class RegrexDemo {
    @BasicTokenInfo(value = {"/\\d{ 1, 3}  abcd\\/ \\d/ [a-bA-H1-4]{1,5}/" , "/[a-z][A-Z][0-9]/", "/\\w\\W\\s\\S\\d\\D/", "/\\d{5,10}/"})
    private String regrex;
}
public class Example02 {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(RegrexDemo.class);
            System.out.println(mock);
        }

    }
}
