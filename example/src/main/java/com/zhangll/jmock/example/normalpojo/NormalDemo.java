package com.zhangll.jmock.example.normalpojo;

import com.zhangll.jmock.core.AnnotationMockContext;

public class NormalDemo {
    public static void main(String[] args) {
        Father mock = new AnnotationMockContext().mock(Father.class);
        System.out.println(mock);
    }
}
