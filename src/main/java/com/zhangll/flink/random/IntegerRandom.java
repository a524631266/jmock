package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.Random;

public class IntegerRandom extends AbstractRandom{
    public Rule<Integer> defaultRule = new DefaultIntegerRule();
    public static int random(){
        int i = new Random().nextInt(100);
//        System.out.println(i);
        return i;
    }

    /**
     * 根据rule计算
     * @param rule
     * @return
     */
    @Override
    public Object compute(Field declaredField,Rule rule){
        if (rule == null){
            return defaultRule.apply();
        }else {
            return rule.apply();
        }
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == Integer.class || type == int.class;
    }

    @Override
    public Rule getRule() {
        // TODO
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        return new DefaultIntegerRule(
                fieldToken.getMin(),
                fieldToken.getMax(),
                fieldToken.getCount(),
                fieldToken.getValue());
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
        final int min ;
        final int max;
        // 表达式中的count 表示一个固定值
        final int count;
//        // step用来给指定的array做递增用的
//        final int step;
        // value 这个只有在 +1step的时候才会初始化
        final String value;


        public DefaultIntegerRule() {
            this(0, 100, 0, null);
        }

        /**
         *
         * @param min  integer最小值
         * @param max integer 最大值
         * @param value 默认值
         */
        public DefaultIntegerRule(int min, int max, int count, String value) {
            this.min = min;
            this.max = max;
            this.count = count;
            this.value = value;
        }

        @Override
        public Integer apply() {
            if(count != 0){
                return count;
            }
            int gap = max - min;
            int i = new Random().nextInt(gap) + min;
            return i;
        }
    }
}
