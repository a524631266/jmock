package com.zhangll.jmock.core.enumt;

import com.zhangll.jmock.core.Father;
import com.zhangll.jmock.core.expression.regrex.DigitLetter;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EnumApiTest {

    @Test
    public void test(){
     // 获取枚举的常量

        /**
         * 获取枚举函数
         */
        Arrays.stream(DigitLetter.class.getEnumConstants()).forEach(digitLeeter ->
        {
//            System.out.println(digitLeeter.getLetter());
        });
        // 收集编程字符串
        String collect = Arrays.stream(DigitLetter.class.getEnumConstants()).map(digitLeeter -> digitLeeter.getLetter())
                .collect(Collectors.joining());
        List<String> collect1 = Arrays.stream(DigitLetter.class.getEnumConstants()).map(digitLeeter -> digitLeeter.getLetter())
                .collect(Collectors.toList());

        Class<DigitLetter> digitLeeterClass = DigitLetter.class;
//        System.out.println(digitLeeterClass);
    }

    /**
     * 根据 getEnumConstants数据来判断是否为枚举类型
     */
    @Test
    public void determineEnum(){
        Field[] declaredFields = Father.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            Object[] enumConstants = declaredField.getType().getEnumConstants();
//            System.out.println(enumConstants);
            assertEquals(null, enumConstants);
        }
    }


}
