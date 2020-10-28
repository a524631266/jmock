package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class LongRandom extends AbstractRandom{
    public Rule<Long> defaultRule = new DefaultLongRule();
    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Long.class || type == long.class;
    }

    @Override
    public Rule getRule() {
        // TODO
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        return new DefaultLongRule(
                fieldToken.getMin(),
                fieldToken.getMax(),
                fieldToken.getCount(),
                fieldToken.getValue());
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
     * 'name|min-max': number
     *  1. 'name|1-10': 1  min-max为最大最小值， number = 1 表示是一个整数类型，不具有任何效应
     *  2. 'name| 5'
     * =>
     */
    public static class DefaultLongRule implements Rule<Long>{
        final long min ;
        final long max;
        // 表达式中的count 表示一个固定值
        final long count;
        //        // step用来给指定的array做递增用的
//        final int step;
        // value 这个只有在 +1step的时候才会初始化
        final String value;


        public DefaultLongRule() {
            this(0, 100, 0, null);
        }

        /**
         *
         * @param min  integer最小值
         * @param max integer 最大值
         * @param value 默认值
         */
        public DefaultLongRule(int min, int max, int count, String value) {
            this.min = min;
            this.max = max;
            this.count = count;
            this.value = value;
        }

        @Override
        public Long apply() {
            if(count != 0){
                return count;
            }
            // 概率
            double p = new Random().nextDouble();
            Double len = (this.max - this.min) * p;

            return  len.longValue() + min;
//            BigDecimal i = new BigDecimal(new Random().nextLong());
//            BigDecimal max = new BigDecimal(this.max);
//            BigDecimal min = new BigDecimal(this.min);
//            BigDecimal a = min.subtract(i);
//            BigDecimal b = max.subtract(i).add(min.subtract(i));
//            Double aDouble = (Long.valueOf(a.toString()) / Math.pow(2, 32));
//            Double bDouble = (Long.valueOf(b.toString()) / Math.pow(2, 32));
//            BigDecimal p = min.subtract(i).divide(max.subtract(i).add(min.subtract(i)));
//
//            BigDecimal multiply = max.subtract(min).multiply(p);
//            BigInteger bigInteger = multiply.add(min).toBigInteger();
//            Long aLong = Long.valueOf(bigInteger.toString());
//            return aLong;
        }
    }
}
