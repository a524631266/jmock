package com.zhangll.flink.random;

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



    /**
     * 根据解析规则 name中的range进行匹配
     */
    public static class DefaultIntegerRule implements Rule<Integer>{
        final int start ;
        final int end;

        public DefaultIntegerRule() {
            this(0 , 100);
        }
        public DefaultIntegerRule(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public Integer apply() {
            int gap = end - start;
            int i = new Random().nextInt(gap) + start;
            return i;
        }
    }
}
