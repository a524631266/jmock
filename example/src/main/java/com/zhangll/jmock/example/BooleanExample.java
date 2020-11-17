package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.ToString;

@ToString
class BooleanPojo{
    @BasicTokenInfo(value = {"true", "false", "false"})
    private Boolean bool1;

    private boolean bool2;
    // min / (min + max)
    @BasicTokenInfo(min = "1" , max = "100")
    private boolean bool3;
}

public class BooleanExample {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(BooleanPojo.class);
            System.out.println(mock);
        }
    }
}
