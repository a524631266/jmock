package com.zhangll.jmock.core.random;

import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.rule.Rule;
import com.zhangll.jmock.core.uitl.RandomUtil;
import com.zhangll.jmock.core.model.FieldNode;

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

    @Override
    protected Character doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        String[] value = currentTokenInfo.getValue();

        if(value.length > 0){
            int cutgap = currentState.getProgress() % value.length;
            if(currentState.getStep()>0){
                return value[cutgap].toCharArray()[0];
            }
            return value[(value.length-1) + cutgap].toCharArray()[0];
        }
        final int min = currentTokenInfo.getMin();
        final int max = currentTokenInfo.getMax();

        Object object;
        if((object = currentState.getPreObject())== null) {
            // 语义 表示 当step > 0 从最小开始计数
            if(currentState.getStep() > 0 ){
                return (char)min;
            }
            // 当step<0 则从最大开始计数
            return (char) max;
        }
        int cutgap = currentState.getProgress() % (max - min + 1);

        if(currentState.getStep() > 0){
            return (char) (min + cutgap);
        }else{
            return (char) (max + cutgap);
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
            int i = RandomUtil.getMin2Max(fieldToken.getMin(), fieldToken.getMax());
            return (char)i;
        }
    }
}
