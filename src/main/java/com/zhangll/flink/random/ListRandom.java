package com.zhangll.flink.random;

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
    public DefaultListRule defaultRule = new DefaultListRule();

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
    public Object compute(Field declaredField, Rule rule) {
        if(rule == null){
            return defaultRule.apply(declaredField);
        } else{
            return rule.apply();
        }
    }

    /**
     * 根据解析规则 name中的range进行匹配
     */
    public class DefaultListRule implements Rule<List<T>> {
        final int start;
        final int end;
        // count 为数量
        final int count = 0;
        // step为以多少自增
        final int step = 1;
        final List<T> defaultList = new ArrayList<>();

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public DefaultListRule() {
            this(0, 5);
        }

        public DefaultListRule(int start, int end) {
            this.start = start;
            this.end = end;
        }

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
                    for (int i = start; i < end; i++) {
                        // TODO RULE转换
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
