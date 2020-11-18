package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
class Father1{
    @BasicTokenInfo(min = "50", max = "60")
    private int age1;
    private boolean sex1;

    @BasicTokenInfo(min = "30000", max = "300000")
    private float money1;
}
@Data
@ToString
class Child1 extends Father1{
    @BasicTokenInfo(min = "30", max = "40")
    private int age2;
    private boolean sex2;
}
@Data
@ToString
class Child2 extends Child1{
    @BasicTokenInfo(min = "10", max = "20")
    private int age3;
}
public class TestForExetends {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Child2 mock = (Child2) annotationMockContext.mock(Child2.class);
            System.out.println(mock);
            System.out.println(mock.getAge1());
            System.out.println(mock.getAge2());
            System.out.println(mock.getAge3());
            System.out.println(mock.getMoney1());
        }
    }
}
