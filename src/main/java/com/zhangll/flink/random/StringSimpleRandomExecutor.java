package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.RandomUtil;

import java.util.Arrays;
import java.util.Collections;

public class StringSimpleRandomExecutor extends AbstractRandomExecutor {
    private DefaultStringRule defaultStringRule = new DefaultStringRule(
            new FieldToken.FieldTokenBuilder()
                    .setMin(1)
                    .setMax(3)
                    .build()
    );






    @Override
    public boolean isCurrentType(Class<?> type) {
        return type == String.class;
    }

    @Override
    public Rule getDefaultRule() {
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
        if(fieldToken == null){
            return defaultStringRule;
        }
        return new DefaultStringRule(fieldToken);
    }

    @Override
    protected String doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        String[] value = currentTokenInfo.getValue();
        if(value.length > 0){
            int cutgap = currentState.getProgress() % value.length;
            if(currentState.getStep()>0){
                return value[cutgap];
            }
            return value[(value.length-1) + cutgap];
        }
        return null;
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
        public String apply(MockContext mockContext, FieldNode fieldNodeContext) {
            // 初始化value
            String[] value = null;
            if(fieldToken.getValue()!=null && (fieldToken.getValue().length > 0)){
//                result = new ValueExpression(value).generate();
                value = fieldToken.getValue();
            }
            if(value == null) {
                value =new String[]{};
            }
            int num = fieldToken.getCount();
            if(num == 0) {
                // 当获取count 为0,说明只设置了values
                if(!isStringEmpty(value)){
                    num = 1;
                }else{
                    num = RandomUtil.getMin2Max(fieldToken.getMin(), fieldToken.getMax());
                }

            } else if( num > 1 && !isStringEmpty(value)){
                // count >1 并且 value =非 ({}，{""}}
                throw new IllegalArgumentException("should set String count be no more than 1:" + fieldToken);
            }
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < num; i++) {
                if(!isStringEmpty(value)){
                    Collections.shuffle(Arrays.asList(value));
                    result.append(value[0]);
                }else{
                    result.append(RandomUtil.randomOneWord());
                }
            }
            return result.toString();
        }

        private boolean isStringEmpty(String[] value) {
            return value.length==0 || (value.length==1 && "".equals(value[0]) );
        }
    }

}
