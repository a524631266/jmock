package com.zhangll.flink.rule;

import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;

/**
 * 定义各种规则
 *  1. 属性名和生成规则之间 用| 分隔
 *  2. 生成规则可选
 * 1. 规则 age==> filed | condition ： value
 *  'name|min-max': value  表示 field为name：拥有最大值与最小值（int 类型 ）
 *  'name|count': value
 *  'name|min-max.dmin-dmax': value  【浮点数】 整数部分大于小于多少
 *  'name|min-max.dcount': value
 *  'name|count.dmin-dmax': value
 *  'name|count.dcount': value
 *
 *  'name|+step': value 一般用于列表集合数据
 */
public interface Rule<T> {
    T apply(MockContext mockContext, FieldNode fieldNodeContext);
}
