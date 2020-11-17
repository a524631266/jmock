package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import com.zhangll.jmock.core.annotation.ContainerTokenInfo;
import com.zhangll.jmock.core.annotation.PojoTokenInfo;
import com.zhangll.jmock.core.annotation.TokenMapping;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ToString
class Father{
    Son son;
}
@ToString
class Son {
    Son2 son2;
    private Date date;
}
@ToString
class Son2 {
    @BasicTokenInfo(min = "1" , max = "100")
    private int a;
}
public class NestPojoDemo {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(Father.class);
            System.out.println(mock);
        }
    }
}
