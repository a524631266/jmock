package com.zhangll.flink.random;

import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;

/**
 * 在Mock体系中属于执行器的范围
 */
public abstract class AbstractRandom implements RandomType {
    @Override
    public void updateField(Object o, Field declaredField, Rule rule) throws IllegalAccessException {
        declaredField.set(o,compute(declaredField, rule));
    }

    /**
     * 每个继承Random的类，都需要计算这个内容
     *
     * @param declaredField
     * @param rule
     * @return
     */
    public abstract Object compute(Field declaredField, Rule rule);

}
