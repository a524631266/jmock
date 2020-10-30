package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.Random;

public class ShortRandom extends AbstractRandom{
    public Rule<Short> defaultRule = new DefaultShortRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(1).setMax(1000).build()
    );

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Short.class || type == short.class;
    }


    @Override
    public Rule getRule() {
        // TODO
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        // TODO
        if(fieldToken == null){
            return getRule();
        }
        return new DefaultShortRule(fieldToken);
    }

    @Override
    public Object compute(Field declaredField, Rule rule) {
        if (rule == null){
            return defaultRule.apply();
        }else {
            return rule.apply();
        }
    }


    /**
     * 根据解析规则 name中的range进行匹配
     * {
     *     "name|10":[{"age|+1":10}]
     * }
     *
     *
     * 'name|min-max': number
     *  1. 'name|1-10': 1  min-max为最大最小值， number = 1 表示是一个整数类型，不具有任何效应
     *  2. 'name| 5'
     * =>
     */
    public static class DefaultShortRule implements Rule<Short>{

        private FieldToken fieldToken;

        public DefaultShortRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Short apply() {

            if(fieldToken.getCount() != 0){
                return (short) fieldToken.getCount();
            }
            int gap = fieldToken.getMax() - fieldToken.getMin();
            int i = new Random().nextInt(gap) + fieldToken.getMin();
            return (short)i;
        }
    }
}
