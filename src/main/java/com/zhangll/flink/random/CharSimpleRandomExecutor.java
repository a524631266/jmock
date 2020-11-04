package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;

import java.util.Random;

public class CharSimpleRandomExecutor extends AbstractRandomExecutor {
    public Rule<Character> defaultRule = new DefaultCharRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(1).setMax(1000).build()
    );


    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == char.class || type == Character.class;
    }

    @Override
    public Rule getDefaultRule() {
        // TODO
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        if(fieldToken == null){
            return getDefaultRule();
        }
        return new DefaultCharRule(fieldToken);
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
    public static class DefaultCharRule implements Rule<Character>{

        private FieldToken fieldToken;

        public DefaultCharRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Character apply(MockContext mockContext, FieldNode fieldNodeContext) {

            if(fieldToken.getCount() != 0){
                return (char) fieldToken.getCount();
            }
            int gap = fieldToken.getMax() - fieldToken.getMin();
            int i = new Random().nextInt(gap) + fieldToken.getMin();
            return (char)i;
        }
    }
}
