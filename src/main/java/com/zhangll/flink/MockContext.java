package com.zhangll.flink;

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
     * @param path
     * @return
     */
    public Object doMock(Class<?> personClass , String path) {
        // 1.首先处理各种token，并设置到mappingStore
        initMapping(personClass, path );
        try {

            // 2. 先通过一个构造函数来获取数据
            Object o = personClass.newInstance();
            Field[] declaredFields = personClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                // 根据 生成的对象和类型来设置值
                assignRandom(o, declaredField, personClass, path);
            }
            return o;
        } catch ( InstantiationException e ) {
            throw new RuntimeException(personClass.getName() + "初始化失败");
        } catch ( IllegalAccessException e ) {
            throw new RuntimeException("非法参数");
        }
    }

    protected abstract void initMapping(Class<?> personClass, String path);

    private void assignRandom(Object o, Field declaredField, Class<?> type, String path) throws IllegalAccessException {
        RandomType random = RandomFactory.getRandom(declaredField.getType());
        if(random!=null){
            Rule rule = mappingStore.getRule(type, declaredField);
            random.updateField(o, declaredField, rule == null? random.getRule(): rule);
        }else{
            // other type 非自定义类型
//            System.out.println(declaredField.getType());
            Object subObject = doMock(declaredField.getType(), path);
            declaredField.set(o, subObject);
        }

    }

}
