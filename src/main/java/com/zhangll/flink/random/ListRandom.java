package com.zhangll.flink.random;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.model.FieldTokenFactory;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;
import com.zhangll.flink.type.RuleTransfer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * List => list + set
 * @param <T>
 */
public class ListRandom<T> extends AbstractRandom{
    public DefaultListRule defaultRule = new DefaultListRule(
            new FieldToken.FieldTokenBuilder()
                    .setCount(10)
                    .setSubFieldToken(
                            new FieldToken.FieldTokenBuilder()
                            .setMin(1)
                            .setMax(2)
                            .setDmin(1)
                            .setDmax(5)
                            .build()
                    )
                    .build()
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
            return ((DefaultListRule) rule).apply(declaredField);
//                return rule.apply();
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
    public class DefaultListRule implements Rule<Collection<T>> {
        final Collection<T> defaultList = null;
        private final FieldToken fieldToken;

        public FieldToken getFieldToken() {
            return fieldToken;
        }

        public DefaultListRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Collection<T> apply() {
            return defaultList;
        }

        /**
         * @param declaredField
         * @return
         */
        public Collection<T> apply(Field declaredField) {
            // 当前的list类型
            Class<?> listType = declaredField.getType();
            Type genericType = declaredField.getGenericType();
            Collection o = null;
            try {
                if(listType == List.class){
                    o = new ArrayList();
                } else if(listType == Set.class){
                    o = new HashSet();
                }else{
                    o = (List) listType.newInstance();
                }
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            }
            // 元素数量
            int elementNum = (fieldToken.getMax() - fieldToken.getMin()) == 0 ?
                    fieldToken.getCount():fieldToken.getMax() - fieldToken.getMin()
                    ;
            if(genericType instanceof ParameterizedType){
                Class type = (Class)((ParameterizedType) genericType).getActualTypeArguments()[0];
                AbstractRandom random = (AbstractRandom) RandomFactory.getRandom(type);
                for (int i = 0; i < elementNum; i++) {
                    // 通过子token规则获取subFiledToken内容
                    Rule rule = random.getRule(fieldToken.getSubFieldToken());
                    Object randomValue = random.compute(null, rule);
                    o.add(randomValue);
                }
            } else{
                throw  new IllegalArgumentException(" list must has type");
            }
            return o;
        }
    }
}
