package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;

import java.util.Random;

public class LongSimpleRandomExecutor extends AbstractRandomExecutor {
    public Rule<Long> defaultRule = new DefaultLongRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(10)
                    .setMax(1000).build()
    );
    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Long.class || type == long.class;
    }

    @Override
    public Rule getDefaultRule() {
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        if(fieldToken == null) {
            return getDefaultRule();
        }
        return new DefaultLongRule(fieldToken);
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
        final FieldToken fieldToken;

        public DefaultLongRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Long apply(MockContext mockContext, FieldNode fieldNodeContext) {
            if(fieldToken.getCount() != 0){
                return Long.valueOf(fieldToken.getCount());
            }
            // 概率
            double p = new Random().nextDouble();
            Double len = (fieldToken.getMax() - fieldToken.getMin()) * p;

            return  len.longValue() + fieldToken.getMin();
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
