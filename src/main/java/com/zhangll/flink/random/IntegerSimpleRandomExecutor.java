package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;

import java.util.Random;

public class IntegerSimpleRandomExecutor extends AbstractRandomExecutor {
    public Rule<Integer> defaultRule = new DefaultIntegerRule(
            new FieldToken.FieldTokenBuilder()
            .setMin(1).setMax(1000).build()
    );
    public static int random(){
        int i = new Random().nextInt(100);
        return i;
    }


    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Integer.class || type == int.class;
    }

    @Override
    public Rule getRule() {
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        if(fieldToken == null){
            return getRule();
        }
        return new DefaultIntegerRule(fieldToken);
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
    public static class DefaultIntegerRule implements Rule<Integer>{

        private FieldToken fieldToken;



        public DefaultIntegerRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Integer apply(MockContext mockContext, FieldNode fieldNodeContext) {

            if(fieldToken.getCount() != 0){
                return fieldToken.getCount();
            }
            int gap = fieldToken.getMax() - fieldToken.getMin();
            int i = new Random().nextInt(gap) + fieldToken.getMin();
            return i;
        }

    }
}
