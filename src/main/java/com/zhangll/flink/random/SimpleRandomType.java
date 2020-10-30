package com.zhangll.flink.random;

import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;

public interface SimpleRandomType extends RandomType{
    /**
     *
     * @param o 空对象
     * @param declaredField 对象的属性列
     * @param rule 规则
     */
    void updateField(Object o, Field declaredField, Rule rule) throws IllegalAccessException;

}
