package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;

public interface SimpleRandomType extends RandomType{
    /**
     *
     * @param target 要被赋值的对象
     * @param declaredField 对象的属性列
     * @param rule 规则
     */
    void updateField(Object target, Field declaredField, Rule rule);

    /**
     *
     * @param target 要被赋值的对象
     * @param fieldNodeContext
     * @throws IllegalAccessException
     */
    void updateField(Object target, FieldNode fieldNodeContext);

}
