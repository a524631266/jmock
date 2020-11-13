package com.zhangll.jmock.core.random;


import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.rule.Rule;

import java.lang.reflect.Field;

/**
 * 随机类型，
 *
 */
public interface RandomType {
    boolean isCurrentType(Class<?> type);


    /**
     * 获取当前默认执行器的Rule，一般默认为单例模式
     * @return
     */
    Rule getDefaultRule();

    /**
     * 根据token结果集获取指定类型的rule
     * 实时获取实例
     * @param fieldToken 词法分析结果
     * @return
     */
    Rule getRule(FieldToken fieldToken);
}
