package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.RandomUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class StringRandom extends AbstractRandom{
    private DefaultStringRule defaultStringRule = new DefaultStringRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(1)
                    .setMax(3)
                    .build()
    );


    public static String random(){
        // 1. 获取长度
        int len = new Random().nextInt(10);
        len = len ==0?1:len;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(CharRandom.random());
        }
        return sb.toString();
    }





    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == String.class;
    }

    @Override
    public Rule getRule() {
        return defaultStringRule;
    }

    /**
     * count 优先级高于 min - max
     * @param fieldToken 词法分析结果
     * @return
     */
    @Override
    public Rule getRule(FieldToken fieldToken) {
        //
        return new DefaultStringRule(fieldToken);
    }

    @Override
    public Object compute(Field declaredField, Rule rule) {

        if(rule == null) {
            return defaultStringRule.apply();
        }else{
            return rule.apply();
        }
    }


    /**
     * 属性值是字符串 String
     * 'name|min-max': 'value' 通过重复 'value' 生成一个字符串，重复次数大于等于 min，小于等于 max。
     * 'name|count': 'value' 通过重复 'value' 生成一个字符串，重复次数等于 count。
     * "name|2": "age" => name: ageage
     */
    public class DefaultStringRule implements Rule<String> {
        private final FieldToken fieldToken;

        public DefaultStringRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        /**
         * 根据实际情况进行rule分解
         * @return
         */
        @Override
        public String apply() {
            // 初始化value
            String[] value = null;
            if(fieldToken.getValue()!=null && (fieldToken.getValue().length > 0)){
//                result = new ValueExpression(value).generate();
                value = fieldToken.getValue();
            }
            int num = fieldToken.getCount();
            if(num == 0) {
                // 当获取count 为0,说明只设置了values
                if(value!=null){
                    num = 1;
                }else{
                    num = RandomUtil.getMin2Max(fieldToken.getMin(), fieldToken.getMax());
                }

            } else if( num > 1 && value != null){
                throw new IllegalArgumentException("should set String count be no more than 1:" + fieldToken);
            }
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < num; i++) {
                if(value != null){
                    Collections.shuffle(Arrays.asList(value));
                    result.append(value[0]);
                }else{
                    result.append(RandomUtil.randomOneWord());
                }
            }
            return result.toString();
        }
    }

}
