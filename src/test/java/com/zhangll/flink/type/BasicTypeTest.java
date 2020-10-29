package com.zhangll.flink.type;

import com.zhangll.flink.Father;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

public class BasicTypeTest {
    @Test
    public void testInteger() throws NoSuchFieldException {
        Field age = Father.class.getDeclaredField("age");
        assertTrue(BasicType.isInteger(age.getType()));
        assertTrue(BasicType.isInteger(Integer.class));
        assertTrue(BasicType.isPrimitive(age.getType()));
    }
    @Test
    public void testString() throws NoSuchFieldException {
        Field name = Father.class.getDeclaredField("name");
        assertTrue(BasicType.isString(name.getType()));
        assertTrue(BasicType.isString(String.class));
        assertFalse(BasicType.isPrimitive(name.getType()));
    }

    @Test
    public void testChar(){

    }


    /**
     * 暂时不考虑泛型
     * @param <T>
     */
    class ClassA<T>{
        private int a = 0;
        private ClassA<T> gg;
    }

    /**
     * 判断是否是基本类型
     * @throws NoSuchFieldException
     */
    @Test
    public void testPrimitive() throws NoSuchFieldException {
        assertTrue(BasicType.isPrimitive(int.class));
        assertTrue(BasicType.isPrimitive(long.class));
        assertTrue(BasicType.isPrimitive(short.class));
        assertTrue(BasicType.isPrimitive(char.class));
        assertTrue(BasicType.isPrimitive(float.class));
        assertTrue(BasicType.isPrimitive(double.class));

        // 包装类型不是基本类型
        assertFalse(BasicType.isPrimitive(Integer.class));
    }

    @Test
    public void testList(){
        assertTrue(BasicType.isCollection(List.class));
        assertTrue(BasicType.isCollection(ArrayList.class));
        assertTrue(BasicType.isCollection(LinkedList.class));
        assertTrue(BasicType.isCollection(Vector.class));
        assertTrue(BasicType.isCollection(Set.class));
    }

}