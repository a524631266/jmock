package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.model.FieldTokenFactory;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;
import com.zhangll.flink.type.RuleTransfer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListRandom<T> extends AbstractRandom{
    public DefaultListRule defaultRule = new DefaultListRule(
            FieldTokenFactory.getDefaultFieldToken()
    );

    @Override
    public boolean isCurrentType(Class<?> type) {
        return BasicType.isList(type);
    }

    @Override
    public Rule getRule() {
        // TODO
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        // TODO
        return new DefaultListRule(fieldToken);
    }


    @Override
    public Object compute(Field declaredField, Rule rule) {
        if(rule == null){
            return defaultRule.apply(declaredField);
        } else{
            return rule.apply();
        }
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
    public class DefaultListRule implements Rule<List<T>> {
//        final int start;
//        final int end;
//        // count 为数量，list数量
//        final int count = 0;
//        // step为在用户有+1的情况下获取数据
//        final int step = 1;
//        final String value;
        final List<T> defaultList = new ArrayList<>();
        private final FieldToken fieldToken;

        public FieldToken getFieldToken() {
            return fieldToken;
        }

        public DefaultListRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

//        public int getStart() {
//            return start;
//        }
//
//        public int getEnd() {
//            return end;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//        public int getCount() {
//            return count;
//        }
//
//        public DefaultListRule() {
//            this(0, 5, null);
//        }
//
//        public DefaultListRule(int start, int end, String value) {
//            this.start = start;
//            this.end = end;
//            this.value = value;
//        }

        @Override
        public List<T> apply() {
            return defaultList;
        }

        /**
         * @param declaredField
         * @return
         */
        public List<T> apply(Field declaredField) {
            List o = null;
            try {
                if(declaredField.getType() == List.class){
                    o = new ArrayList();
                } else{
                    o = (List) declaredField.getType().newInstance();
                }
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            }
            if(declaredField.getGenericType() instanceof ParameterizedType){
                Class type = (Class)((ParameterizedType) declaredField.getGenericType()).getActualTypeArguments()[0];
                RandomType random = RandomFactory.getRandom(type);
                if (random instanceof IntegerRandom) {
                    for (int i = 0; i < (fieldToken.getMax() - fieldToken.getMin()); i++) {
                        // TODO RULE转换
                        // 上层的rule 转换 成下层的rule
                        Rule rule = RuleTransfer.transferToIntRule(this);
                        Object randomValue = ((IntegerRandom) random).compute(null, rule);
//                        System.out.println(randomValue);
                        o.add(randomValue);
                    }
                }
            }
            return o;
        }
    }
}
