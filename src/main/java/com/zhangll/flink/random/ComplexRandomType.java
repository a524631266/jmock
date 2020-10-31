package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;

public interface ComplexRandomType extends RandomType{
    /**
     *
     * @param target 要被赋值的对象
     * @param declaredField 用来给对象赋值的field对象
     * @param rule 根据规则定义的
     * @param context
     * @throws IllegalAccessException
     */
    void updateField(Object target, Field declaredField, Rule rule, MockContext context);

    /**
     *
     * @param target 要被赋值的对象
     * @param context
     * @param fieldNodeContext 词法分析的上下文对象,可以用于查找上下字节点
     * @throws IllegalAccessException
     */
    void updateField(Object target,  MockContext context, FieldNode fieldNodeContext);

}
