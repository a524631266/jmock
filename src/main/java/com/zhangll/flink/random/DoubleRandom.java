package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.model.FieldTokenFactory;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.RandomUtil;

import java.lang.reflect.Field;
import java.util.Random;

public class DoubleRandom extends AbstractRandom{
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
    public Rule getRule() {
        // TOCO
        return defaultDoubleRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        // TODO
        return new DefaultDoubleRule(fieldToken);
    }

    @Override
    public Object compute(Field declaredField, Rule rule) {
        return rule.apply();
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
//        final int min;
//        final int max;
//        // 最小保留位数
//        final int dmin;
//        // 最小保留位数
//        final int dmax;
//        final String value;
//
//        public DefaultDoubleRule() {
//            this(0,0,0,0,null);
//        }
//
//        public DefaultDoubleRule(int min, int max, int dmin, int dmax, String value) {
//            this.min = min;
//            this.max = max;
//            this.dmin = dmin;
//            this.dmax = dmax;
//            this.value = value;
//        }

        @Override
        public Double apply() {
            // value 的值不为0 的时候，要根据value的小数位来设置小数点
            int integral = 0;
            int frac = 0;
            String fractional = "";
            if (fieldToken.getValue() != null) {
                fractional = fieldToken.getValue().split("\\.")[1];
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
