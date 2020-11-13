package com.zhangll.jmock.core.random;


import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.rule.Rule;
import com.zhangll.jmock.core.type.BasicType;
import com.zhangll.jmock.core.uitl.RandomUtil;


import java.lang.reflect.Array;

/**
 *
 */
public class ArrayRandomExecutor<T> extends AbstractRandomExecutor {

    public DefaultArrayRule defaultRule = new DefaultArrayRule(
            new FieldToken.FieldTokenBuilder()
                    .setCount(10)
                    .build()
    );

    @Override
    public boolean isCurrentType(Class<?> type) {
        return BasicType.isArray(type);
    }

    @Override
    public Rule getDefaultRule() {
        return defaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        if(fieldToken == null) {
            return defaultRule;
        }
        return new DefaultArrayRule(fieldToken);
    }

    @Override
    protected Object doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        //TODO
        return null;
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
        public Object[] apply(MockContext mockContext, FieldNode fieldNodeContext) {
//            assert(fieldNodeContext.getCurrentTokenInfo() == fieldToken);
            // 当前的list类型
            if(!fieldNodeContext.isArray()){
                throw new IllegalArgumentException("must be array");
            }
            Class<?> componentType = fieldNodeContext.getComponentType();

            // 元素数量
            int elementNum =fieldToken.getCount() == 0
                    ? RandomUtil.getMin2Max(fieldToken.getMin(),fieldToken.getMax())
                    :fieldToken.getCount();
            // 基本数据类型不能转化为Object[]
            // 在数组中没有
            Object[] o = (Object[]) Array.newInstance(
                    BasicType.primitiveToWarpper(componentType), elementNum);
            if(fieldNodeContext.getCurrentTokenInfo() != null
                    && fieldNodeContext.getCurrentTokenInfo().getStep()>0
                    && fieldNodeContext.getCurrentTokenInfo().getValue().length>0
                    ){
                int step = fieldNodeContext.getCurrentTokenInfo().getStep();
                String[] value = fieldNodeContext.getCurrentTokenInfo().getValue();
                int valueLength = value.length;
                int count = 0;
                for (int i = 0; i < elementNum; i++) {
                    o[i] = value[(count)  % valueLength];
                    count+= step;
                }
                return o;
            }else {

                RandomType executor = mockContext.getExecutor(componentType);
                for (int i = 0; i < elementNum; i++) {
                    if (fieldNodeContext.innerContainerIsInnerType()) {

                        o[i] = executor.getRule(fieldNodeContext.getInnerBasicTokens()).apply(mockContext, fieldNodeContext);
                    } else {
                        // componentType =  POJO Type
                        o[i] = mockContext.mock(componentType, fieldNodeContext.getDeclaredField(), fieldNodeContext.getInnerPojoTokens());
//                        fieldNodeContext.assignInnerObject(o[i], mockContext);
//                        o[i] = mockContext.mockWithContext(componentType, fieldNodeContext);
                    }
                }
                return o;
            }
        }
    }

}
