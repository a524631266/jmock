package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import com.zhangll.jmock.core.annotation.ContainerTokenInfo;
import com.zhangll.jmock.core.annotation.PojoTokenInfo;
import com.zhangll.jmock.core.annotation.TokenMapping;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
class ListPojo{
    @BasicTokenInfo(min = "1" , max = "3")
    private List<String> names;
    @BasicTokenInfo(min = "1" , max = "3")
    private List<RegrexPojo> regrexPojos;
    @BasicTokenInfo(min = "1" , max = "3")
    private ArrayList<DatePojo> pojos;


    @ContainerTokenInfo(
            innerPojoType = @PojoTokenInfo(
            value = {
                    @TokenMapping(field = "bool3",
                            basicTokenInfo = @BasicTokenInfo(min = "1", max = "2"))
            })
    )
    @BasicTokenInfo(min ="1", max = "1")
    BooleanPojo[] booleanPojos;
}
public class ListExample {
    public static void main(String[] args) {
        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(ListPojo.class);
            System.out.println(mock);
        }
    }
}
