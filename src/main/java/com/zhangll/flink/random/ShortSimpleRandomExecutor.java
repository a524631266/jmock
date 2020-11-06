package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.RandomUtil;

import java.util.Random;

public class ShortSimpleRandomExecutor extends AbstractRandomExecutor {
    public Rule<Short> defaultRule = new DefaultShortRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(1).setMax(1000).build()
    );

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Short.class || type == short.class;
    }


    @Override
    public Rule getDefaultRule() {
        // TODO
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        // TODO
        if(fieldToken == null){
            return getDefaultRule();
        }
        return new DefaultShortRule(fieldToken);
    }

    @Override
    protected Object doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        // TODO
        return null;
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
        public Short apply(MockContext mockContext, FieldNode fieldNodeContext) {

            if(fieldToken.getCount() != 0){
                return (short) fieldToken.getCount();
            }
            int i = RandomUtil.getMin2Max(fieldToken.getMin(), fieldToken.getMax());
            return (short)i;
        }
    }
}
