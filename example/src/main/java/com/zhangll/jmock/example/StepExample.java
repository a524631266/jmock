package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import com.zhangll.jmock.core.annotation.ContainerTokenInfo;
import lombok.ToString;

import java.util.List;

@ToString
class StepPojo{
    // 以 1 - 3 -5- 7 方式递增
    @BasicTokenInfo(min = "1", max = "100", step = "2")
    private int stepi;

    @BasicTokenInfo(min = "1", max = "100", dmin = "1", dmax = "3")
    private double stepd2;

}
public class StepExample {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(StepPojo.class);
            System.out.println(mock);
        }
    }
}
