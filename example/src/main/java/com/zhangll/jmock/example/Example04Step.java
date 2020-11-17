package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import com.zhangll.jmock.core.annotation.ContainerTokenInfo;
import lombok.ToString;

import java.sql.Date;
import java.util.*;

@ToString
class Array01 {
//    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "2", max = "3"))
//    @BasicTokenInfo(min = "2", max = "3")
//    List<String> list;
    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "1", max = "10", step = "2"))
    @BasicTokenInfo(min = "2", max = "3")
    ArrayList<Integer> arrayList;
//    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "1", max = "10"))
//    @BasicTokenInfo(min = "2", max = "3")
//    Set<Boolean> set;
//    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "1", max = "10"))
//    @BasicTokenInfo(min = "2", max = "3")
//    HashSet<Double> hashSet;
//    @ContainerTokenInfo(innerBasicType = @BasicTokenInfo(min = "1", max = "10"))
//    @BasicTokenInfo(min = "2", max = "3")
//    LinkedList<Short> linkedList;

}

public class Example04Step {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        AnnotationMockContext annotationMockContext = new AnnotationMockContext();
        for (int i = 0; i < 100; i++) {
            Object mock = annotationMockContext.mock(Array01.class);
            System.out.println(mock);
        }

    }
}
