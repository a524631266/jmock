package com.zhangll.jmock.core.random;

import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.rule.Rule;
import com.zhangll.jmock.core.uitl.RandomUtil;

import java.util.Random;

public class DoubleSimpleRandomExecutor extends AbstractRandomExecutor {
    private static DefaultDoubleRule defaultDoubleRule = new DefaultDoubleRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(1).setMax(10)
                    .setDmin(1)
                    .setDmax(5).build()
    );
    // 返回 char类型数值
    public static double random() {
        double v = new Random().nextDouble();
        return v;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Double.class || type == double.class;
    }


    @Override
    public Rule getDefaultRule() {
        // TOCO
        return defaultDoubleRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        if(fieldToken == null){
            return getDefaultRule();
        }
        return new DefaultDoubleRule(fieldToken);
    }

    @Override
    protected Double doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        String[] value = currentTokenInfo.getValue();

        if(value.length > 0){
            int cutgap = currentState.getProgress() % value.length;
            if(currentState.getStep()>0){
                return Double.parseDouble(value[cutgap]);
            }
            return Double.parseDouble(value[(value.length-1) + cutgap]);
        }
        final int min = currentTokenInfo.getMin();
        final int max = currentTokenInfo.getMax();
        final int dmax = currentTokenInfo.getDmax();
        final int dmin = currentTokenInfo.getDmin();

        Object object = null;
        if((object = currentState.getPreObject())== null) {
            // 语义 表示 当step > 0 从最小开始计数
            if(currentState.getStep() > 0 ){
                return min * 1.0;
            }
            // 当step<0 则从最大开始计数
            return max * 1.0;
        }
        Double preValue = (Double) object;
        Double nextValue = preValue + currentState.getStep();
        if(nextValue >= (max+1) ){
            nextValue = nextValue - (max+1) + min;
        }else if(nextValue < min ){
            nextValue = max + 1 - (min - nextValue);
        }
        return nextValue;
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
            return Double.valueOf((String) result);
        }
        return super.convertToCurrentType(result);
    }
    /**
     * 'name|min-max.dmin-dmax': number
     * 保留位数
     */
    public static class DefaultDoubleRule implements Rule<Double>{
        private final FieldToken fieldToken;

        public DefaultDoubleRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }


        @Override
        public Double apply(MockContext mockContext, FieldNode fieldNodeContext) {
            // value 的值不为0 的时候，要根据value的小数位来设置小数点
            if(fieldToken.getValue().length > 1) {
                throw new  IllegalArgumentException("Double fieldToken value must be only one parameter");
            }
            int integral = 0;
            int frac = 0;
            String fractional = "";
            if (fieldToken.getValue() != null && fieldToken.getValue().length > 0) {
                fractional = fieldToken.getValue()[0].split("\\.")[1];
            }
            integral = RandomUtil.getMin2Max(fieldToken.getMin(), fieldToken.getMax());
            // 小数部分 计算方式
            int digit = RandomUtil.getMin2Max(fieldToken.getDmin(), fieldToken.getDmax());
            if (digit == 0) {
                digit++;
            }
            //            digit
            int fracLength = fractional.length();
            if(digit <= fracLength){
                frac = Integer.valueOf(fractional.substring(0, digit));
            } else{
                int rest = digit - fracLength;
                Integer value2 = RandomUtil.getMin2Max(1, (int) Math.pow(10 , rest));
                // 结尾不能为0
                frac = Integer.valueOf(fractional + String.format("%0"+ rest+"d", value2));
                if(frac % 10 == 0){
                    frac +=1;
                }
            }
            double trueResult = (integral + frac / Math.pow(10, digit));

            double result = Math.round(trueResult * Math.pow(10, digit)) / Math.pow(10, digit);
            // 消除位数
            return result;
        }
    }

}
