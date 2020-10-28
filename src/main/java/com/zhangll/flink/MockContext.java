package com.zhangll.flink;

import com.zhangll.flink.random.*;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 解析流程
 * @param <T>
 */
public abstract class MockContext<T> {
    private static MappingStore mappingStore = new MappingStore();
//    /**
//     * 默认没有rule，使用默认的rule
//     * @param personClass
//     * @return
//     */
//    public static Object mock(Class<?> personClass) {
//       return mock(personClass, null);
//    }

    /**
     * @param personClass
     * @return
     */
    public static Object mock(Class<?> personClass ) {
        // 1.首先处理各种token，并设置到mappingStore
//        proccessToken();
        try {
            // 2. 先通过一个构造函数来获取数据
            Object o = personClass.newInstance();
            Field[] declaredFields = personClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();
                // 根据 生成的对象和类型来设置值
                assignRandom(o, declaredField, type);
            }
            return o;
        } catch ( InstantiationException e ) {
            throw new RuntimeException("初始化失败");
        } catch ( IllegalAccessException e ) {
            throw new RuntimeException("非法参数");
        }
    }

    private static void assignRandom(Object o, Field declaredField, Class<?> type) throws IllegalAccessException {
        RandomType random = RandomFactory.getRandom(type);
        if(random!=null){
            Rule rule = mappingStore.getRule(type, declaredField);
            random.updateField(o, declaredField, rule == null? random.getRule(): rule);
        }

    }

}
