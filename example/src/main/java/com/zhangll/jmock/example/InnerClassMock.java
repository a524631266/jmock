package com.zhangll.jmock.example;

import com.zhangll.jmock.core.AnnotationMockContext;
import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Data;
import lombok.ToString;

public class InnerClassMock {
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

    public static void main(String[] args) {
        Person.Person2 mock = new AnnotationMockContext().mock(Person.Person2.class);
        System.out.println(mock);
        System.out.println(mock.getAge());
    }
}
