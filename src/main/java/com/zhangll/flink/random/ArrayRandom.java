package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;


import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 *
 */
public class ArrayRandom<T> extends AbstractComplexRandom {

    public DefaultArrayRule defaultRule = new DefaultArrayRule(
            new FieldToken.FieldTokenBuilder()
                    .setCount(10)
                    .build()
    );
    @Override
    public Object compute(Field declaredField, Rule rule, MockContext context) {
        if(rule == null){
            return defaultRule.apply(declaredField, context);
        } else{
            return ((DefaultArrayRule) rule).apply(declaredField, context);
//                return rule.apply();
        }
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return BasicType.isArray(type);
    }

    @Override
    public Rule getRule() {
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        return new DefaultArrayRule(fieldToken);
    }

    /**
     * 根据解析规则 name中的range进行匹配
     *
     * 1.'name|1': array
     *
     * 从属性值 array 中随机选取 1 个元素，作为最终值。
     *
     * 2. 'name|+1': array
     *
     * 从属性值 array 中顺序选取 1 个元素，作为最终值。
     *
     * 3. 'name|min-max': array
     *
     * 通过重复属性值 array 生成一个新数组，重复次数大于等于 min，小于等于 max。
     *
     * 4. 'name|count': array
     *
     * 通过重复属性值 array 生成一个新数组，重复次数为 count。
     */
    public class DefaultArrayRule implements Rule<Object[]> {
        final Object[] defaultList = null;
        private final FieldToken fieldToken;

        public FieldToken getFieldToken() {
            return fieldToken;
        }

        public DefaultArrayRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Object[] apply() {
            return defaultList;
        }

        /**
         * @param declaredField
         * @return
         */
        public Object[] apply(Field declaredField, MockContext context) {
            // 当前的list类型
            if(!BasicType.isArray(declaredField.getType())){
                throw new IllegalArgumentException("must be array");
            }
            Class<?> listType = declaredField.getType().getComponentType();

            // 元素数量
            int elementNum = (fieldToken.getMax() - fieldToken.getMin()) == 0 ?
                    fieldToken.getCount():fieldToken.getMax() - fieldToken.getMin()
                    ;
            // 基本数据类型不能转化为Object[]
            Object[] o = (Object[]) Array.newInstance(
                    BasicType.primitiveToWarpper(listType), elementNum);
            AbstractSimpleRandom random = (AbstractSimpleRandom) RandomFactory.getRandom(listType);
            for (int i = 0; i < elementNum; i++) {
                // 通过子token规则获取subFiledToken内容
                Rule rule = random.getRule(fieldToken.getSubFieldToken());
                Object randomValue = random.compute(null, rule);
                o[i] = randomValue;
            }
            return o;
        }
    }

}
