package com.zhangll.flink;

import com.zhangll.flink.annotation.FieldTokenType;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.random.RandomFactory;
import com.zhangll.flink.random.RandomType;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class AnnotationMockContext extends MockContext {
    final static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static  Map<Class, Boolean> history = new HashMap<>();
    /**
     * 在 注解类中需要关注的是注解，因此，不需要关注path
     * path是用于给配置文件使用的
     *
     * @param personClass
     * @param token
     */
    @Override
    protected void initMapping(Class<?> personClass,  FieldToken token) {
        Field[] declaredFields = personClass.getDeclaredFields();
        boolean hasClass = false;
//        rwl.readLock().lock();
        hasClass = history.getOrDefault(personClass, false);
//        rwl.readLock().unlock();
        if(hasClass){
            return;
        }
        // 写的话只有一次进入
        rwl.writeLock().lock();
        try {
            hasClass =  history.getOrDefault(personClass, false);
            if(!hasClass){
                for (Field declaredField : declaredFields) {
                    FieldTokenType annotation = declaredField.getAnnotation(FieldTokenType.class);
                    FieldToken fieldToken = null;
                    if(annotation != null) {
                        fieldToken = new FieldToken.FieldTokenBuilder()
                                .setMin(Integer.valueOf(annotation.min()))
                                .setMax(Integer.valueOf(annotation.max()))
                                .setDmin(Integer.valueOf(annotation.dmin()))
                                .setDmax(Integer.valueOf(annotation.dmax()))
                                .setCount(Integer.valueOf(annotation.count()))
                                .setValue(annotation.value())
                                .setDcount(Integer.valueOf(annotation.dcount()))
                                .setStep(Integer.valueOf(annotation.step()))
                                .build();

                    }
                    if(token != null){
                        fieldToken = token;
                    }
                    RandomType random = RandomFactory.getRandom(declaredField.getType());
                    if(random!=null){
                        Rule rule = RandomFactory.getRandom(declaredField.getType()).getRule(fieldToken);
                        mappingStore.setRuleMap(personClass, declaredField, rule);
                    }
                }
                history.put(personClass, true);
            }
        }finally {
            rwl.writeLock().unlock();

        }

    }
}
