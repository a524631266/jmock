package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.RandomUtil;

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
    public Rule getDefaultRule() {
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        if(fieldToken == null){
            return getDefaultRule();
        }
        return new DefaultIntegerRule(fieldToken);
    }

    /**
     * @BasicTokenInfo(min="1", step = "2"), 后续所有数据均以[1 ,3, 5...] 递增 直到最大，再重新来
     *
     * @BasicTokenInfo(min="1" , max="5", step = "2"), 后续所有数据均以[1 ,3, 5, 1, 3 ,5 循环递增] round-robin递增
     *
     * @BasicTokenInfo( step = "2", value ={ "1", "10" , "20"}), ==> 1 , 20 , 10, 1 round-robin 循环
     *
     * @param currentTokenInfo
     * @param currentState 当前的状态
     * @return
     */
    @Override
    protected Object doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        String[] value = currentTokenInfo.getValue();

        if(value.length > 0){
            int cutgap = currentState.getProgress() % value.length;
            if(currentState.getStep()>0){
                return Integer.parseInt(value[cutgap]);
            }
            return Integer.parseInt(value[(value.length-1) + cutgap]);
        }
        final int min = currentTokenInfo.getMin();
        final int max = currentTokenInfo.getMax();

        Object object;
        if((object = currentState.getPreObject())== null) {
            // 语义 表示 当step > 0 从最小开始计数
            if(currentState.getStep() > 0 ){
                return min;
            }
            // 当step<0 则从最大开始计数
            return max;
        }
        int cutgap = currentState.getProgress() % (max - min + 1);

        if(currentState.getStep() > 0){
            return min + cutgap;
        }else{
            return max + cutgap;
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
            int i = RandomUtil.getMin2Max(fieldToken.getMin(), fieldToken.getMax());
            return i;
        }

    }
}
