package com.zhangll.flink;

import com.zhangll.flink.random.*;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;

import java.lang.reflect.Field;


public class MockData<T> {
    /**
     * 默认没有rule，使用默认的rule
     * @param personClass
     * @return
     */
    public static Object mock(Class<?> personClass) {
       return mock(personClass, null);
    }

    /**
     *
     * @param personClass
     * @param rule
     * @return
     */
    public static Object mock(Class<?> personClass, Rule rule) {
        // 首先是否一定要有一个构造函数
        try {
            // 1. 先通过一个构造函数来获取数据
            Object o = personClass.newInstance();
            Field[] declaredFields = personClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();
                // 根据 生成的对象和类型来设置值
                assignRandom(o, declaredField, type ,rule);
            }
            return o;
        } catch ( InstantiationException e ) {
            throw new RuntimeException("初始化失败");
        } catch ( IllegalAccessException e ) {
            throw new RuntimeException("非法参数");
        }
    }

    private static void assignRandom(Object o, Field declaredField, Class<?> type, Rule rule) throws IllegalAccessException {
        RandomType random = RandomFactory.getRandom(type);
        if(random!=null){
            random.updateField(o, declaredField, rule);
        }

    }

}
