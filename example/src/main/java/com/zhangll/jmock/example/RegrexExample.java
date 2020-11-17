package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.ToString;

import java.sql.Date;

@ToString
class RegrexPojo {
    @BasicTokenInfo(value = {"/\\d{ 1, 3}  abcd\\/ \\d/ [a-bA-H1-4]{1,5}/" , "/[a-z][A-Z][0-9]/", "/\\w\\W\\s\\S\\d\\D/", "/\\d{5,10}/"})
    private String regrex;

    @BasicTokenInfo(value = "/\\d{1,3}/")
    private int regrexInteger;


    @BasicTokenInfo(value = "/\\d{1,3}\\.\\d{1,6}/")
    private double regrexDouble;

    @BasicTokenInfo(value = "/201[1-8]-0[1-8]-0[1-8]/")
    private Date dateRegrex;


    @BasicTokenInfo(value = "/[a-zA-Z]/")
    private Character charRegrex;


}
public class RegrexExample {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(RegrexPojo.class);
            System.out.println(mock);
        }

    }
}
