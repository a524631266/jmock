package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.RandomUtil;

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
    protected Object doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        //TODO
        return null;
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
