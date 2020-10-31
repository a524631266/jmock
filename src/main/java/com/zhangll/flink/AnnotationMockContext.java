package com.zhangll.flink;

import com.zhangll.flink.annotation.FieldTokenType;
import com.zhangll.flink.model.ASTNode;
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

    @Override
    protected FieldToken initFieldToken(Field field) {
        if(field == null) {
            return null;
        }
        FieldTokenType annotation = field.getAnnotation(FieldTokenType.class);
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
        return fieldToken;
    }
}
