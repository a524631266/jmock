package com.zhangll.jmock.core.random;

import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.rule.Rule;
import com.zhangll.jmock.core.uitl.RandomUtil;

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
    protected Short doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        String[] value = currentTokenInfo.getValue();

        if(value.length > 0){
            int cutgap = currentState.getProgress() % value.length;
            if(currentState.getStep()>0){
                return Short.parseShort(value[cutgap]);
            }
            return Short.parseShort(value[(value.length-1) + cutgap]);
        }
        final int min = currentTokenInfo.getMin();
        final int max = currentTokenInfo.getMax();

        Object object;
        if((object = currentState.getPreObject())== null) {
            // 语义 表示 当step > 0 从最小开始计数
            if(currentState.getStep() > 0 ){
                return (short)min;
            }
            // 当step<0 则从最大开始计数
            return (short)max;
        }
        int cutgap = currentState.getProgress() % (max - min + 1);

        if(currentState.getStep() > 0){
            return (short) (min + cutgap);
        }else{
            return (short) (max + cutgap);
        }
    }

    @Override
    protected Object doHandleCountValue(MockContext context, FieldNode fieldNodeContext) {
        String[] value = fieldNodeContext.getCurrentTokenInfo().getValue();
        Integer index = RandomUtil.getMin2Max(0, value.length - 1);
        return value[index];
    }

    @Override
    protected Object convertToCurrentType(Object result) {
        if(result instanceof String) {
            return Short.valueOf((String) result);
        }
        return super.convertToCurrentType(result);
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
