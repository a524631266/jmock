package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.RandomUtil;

import java.util.Random;

public class BooleanSimpleRandomExecutor extends AbstractRandomExecutor {
    private static DefaultBooleanRule defaultBooleanRule = new DefaultBooleanRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(1)
                    .setMax(1)
                    .setValue(new String[]{"true"}).build()
    );
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
    public Rule getRule() {
        return defaultBooleanRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        if(fieldToken==null) return defaultBooleanRule;
        return new DefaultBooleanRule(fieldToken);
    }

    /**
     * 'name|1': boolean
     * 'name|min-max': value
     * 随机生成一个布尔值，值为 value 的概率是 min / (min + max)，值为 !value 的概率是 max / (min + max)。
     */
    public static class DefaultBooleanRule implements Rule<Boolean> {

        private final FieldToken fieldToken;


        public DefaultBooleanRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Boolean apply(MockContext mockContext, FieldNode fieldNodeContext) {
            boolean leftResult = true;
            if (fieldToken.getValue().equals("true")) {
                leftResult = true;
            } else {
                leftResult = false;
            }

            boolean result = true;
            if (fieldToken.getCount() != 0) {
                result = RandomUtil.getBoolean(fieldToken.getCount(), fieldToken.getCount());
            } else{
                result = RandomUtil.getBoolean(fieldToken.getMin(), fieldToken.getMax());
            }
            return result ? !leftResult: leftResult;
        }
    }

}
