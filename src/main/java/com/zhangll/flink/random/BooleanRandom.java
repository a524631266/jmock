package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.RandomUtil;

import java.lang.reflect.Field;
import java.util.Random;

public class BooleanRandom extends AbstractRandom{
    private static DefaultBooleanRule defaultBooleanRule = new DefaultBooleanRule();
    // 返回 char类型数值
    public static boolean random() {
        boolean v = new Random().nextBoolean();
        return v;
    }

    @Override
    public boolean isCurrentType(Class type) {
        return type == Boolean.class || type == boolean.class;
    }

    @Override
    public Object compute(Field declaredField, Rule rule) {
        return rule.apply();
    }

    @Override
    public Rule getRule() {
        //TODO
        return defaultBooleanRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        // TODO
        return new DefaultBooleanRule(fieldToken.getMin()
                , fieldToken.getMax()
                , fieldToken.getCount()
                , fieldToken.getValue());
    }

    /**
     * 'name|1': boolean
     * 'name|min-max': value
     * 随机生成一个布尔值，值为 value 的概率是 min / (min + max)，值为 !value 的概率是 max / (min + max)。
     */
    public static class DefaultBooleanRule implements Rule<Boolean>{
        final int min;
        final int max;
        final int count ;
        final String value;

        public DefaultBooleanRule() {
            this(0,1, 0,null);
        }

        public DefaultBooleanRule(int min, int max , int count, String value) {
            this.min = min;
            this.max = max;
            this.count = count;
            if(value == null) {
                this.value = "true";
            }else{
                this.value = value;
            }
        }

        @Override
        public Boolean apply() {
            boolean leftResult = true;
            if (this.value.equals("true")) {
                leftResult = true;
            } else {
                leftResult = false;
            }

            boolean result = true;
            if (count != 0) {
                result = RandomUtil.getBoolean(count, count);
            } else{
                result = RandomUtil.getBoolean(this.min, this.max);
            }
            return result ? !leftResult: leftResult;
        }
    }

}
