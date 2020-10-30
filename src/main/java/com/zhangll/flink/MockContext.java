package com.zhangll.flink;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.random.*;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * 解析流程
 */
public abstract class MockContext {
    protected static MappingStore mappingStore = new MappingStore();
    /**
     * 默认没有rule，使用默认的rule
     * @param personClass
     * @return
     */
    public  Object mock(Class<?> personClass) {
       return doMock(personClass, null);
    }

    /**
     *
     * @param personClass
     * @param token
     * @return
     */
    public Object doMock(Class<?> personClass , FieldToken token) {
        // 1.首先处理各种token，并设置到mappingStore
        initMapping(personClass, token );
        try {

            // 2. 先通过一个构造函数来获取数据
            Object o = personClass.newInstance();
            Field[] declaredFields = personClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                // 根据 生成的对象和类型来设置值
                assignRandom(o, declaredField, personClass, token);
            }
            return o;
        } catch ( InstantiationException e ) {
            throw new RuntimeException(personClass.getName() + "初始化失败");
        } catch ( IllegalAccessException e ) {
            throw new RuntimeException("非法参数");
        }
    }

    protected abstract void initMapping(Class<?> personClass, FieldToken token);

    private void assignRandom(Object o, Field declaredField, Class<?> type, FieldToken token) {
        RandomType random = RandomFactory.getRandom(declaredField.getType());
        if (random != null) {
            // 如果是
            Rule rule = mappingStore.getRule(type, declaredField);
            try {
                if(random instanceof AbstractSimpleRandom){

                    ((AbstractSimpleRandom)random).updateField(o, declaredField, rule == null ? random.getRule() : rule);

                }else if (random instanceof  AbstractComplexRandom){
                    ((AbstractComplexRandom)random).updateField(o, declaredField, rule == null ? random.getRule() : rule, this);
                }
            } catch ( IllegalAccessException e ) {
                throw new IllegalArgumentException(declaredField.getName() + ":" + e.getMessage());
            }

        } else {
            // other type 非自定义类型
//            System.out.println(declaredField.getType());
            Object subObject = doMock(declaredField.getType(), token);
            try {
                declaredField.set(o, subObject);
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            }
        }

    }

}
