package com.zhangll.jmock.core;

import com.zhangll.jmock.core.annotation.BasicTokenInfo;
import lombok.Data;
import lombok.ToString;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class MockContextTest {
    private AnnotationMockContext context;
    @Data
    @ToString
    class InnerClass {
        private int age;
        private int money;
        @BasicTokenInfo(value = {"a", "b"})
        private String name;
        @Data
        @ToString
        class InnerClass2 {
            @BasicTokenInfo(value = {"a", "b"})
            private String name2;
        }
    }

    @Before
    public void init(){
        context = new AnnotationMockContext();
    }
    @Test
    public void createNotNullObject() {
        Object object = context.createObject(InnerClass.class, null);
        assertTrue(object != null);
    }

    @Test
    public  void innerClassRandomTest(){

        Set<String> sets = new HashSet<String>() {
            {
                add("a");
                add("b");
            }
        };
        for (int i = 0; i < 100; i++) {
            InnerClass mock = (InnerClass) context.mock(InnerClass.class);
            System.out.printf("%s\n", mock);
            assertTrue(sets.contains(mock.getName()));
        }
    }

    @Test
    public  void innerClass2RandomTest(){

        Set<String> sets = new HashSet<String>() {
            {
                add("a");
                add("b");
            }
        };
        for (int i = 0; i < 100; i++) {
            InnerClass.InnerClass2 mock = (InnerClass.InnerClass2) context.mock(InnerClass.InnerClass2.class);
            assertTrue(sets.contains(mock.getName2()));
        }
    }
}