package com.zhangll.jmock.core;

import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Data;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;

public class InnerClassMockTest {
    private AnnotationMockContext context;
    @Data
    @ToString
    class Person {
        @BasicTokenInfo(min = "200", max = "1000")
        private int age;
        @ToString
        class Person2{
            private int age2;
            public int getAge() {
                return age;
            }
        }
    }
    @Before
    public void init(){
        context = new AnnotationMockContext();
    }
    @Test
    public void testInnerClass() {
        Person.Person2 mock = context.mock(Person.Person2.class);

        System.out.println(mock);
        System.out.println(mock.getAge());
    }
}
