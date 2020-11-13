package com.zhangll.jmock.core.random;

import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.rule.Rule;
import com.zhangll.jmock.core.type.BasicType;
import com.zhangll.jmock.core.uitl.RandomUtil;
import com.zhangll.jmock.core.model.FieldNode;

import java.lang.reflect.Type;
import java.util.*;

/**
 * List => list + set
 * @param <T>
 */
public class ListRandomExecutor<T> extends AbstractRandomExecutor {
    public DefaultListRule defaultRule = new DefaultListRule(
            new FieldToken.FieldTokenBuilder()
                    .setCount(10)
//                    .setSubFieldToken(
//                            new FieldToken.FieldTokenBuilder()
//                            .setMin(1)
//                            .setMax(2)
//                            .setDmin(1)
//                            .setDmax(5)
//                            .build()
//                    )
                    .build()
    );

    @Override
    public boolean isCurrentType(Class<?> type) {
        return BasicType.isCollection(type);
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
        return new DefaultListRule(fieldToken);
    }

    @Override
    protected Object doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        // TODO
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
        public Collection<T> apply(MockContext mockContext, FieldNode fieldNodeContext) {
            // 当前的list类型
            Class<?> listType = fieldNodeContext.getType();
            Type genericType = fieldNodeContext.getDeclaredField().getGenericType();
            Collection o = null;
            try {
                if (listType == List.class) {
                    o = new ArrayList();
                } else if (listType == Set.class) {
                    o = new HashSet();
                } else {
                    o = (Collection) listType.newInstance();
                }
            } catch ( InstantiationException e ) {
                e.printStackTrace();
            } catch ( IllegalAccessException e ) {
                e.printStackTrace();
            }

            // 元素数量

            int elementNum =fieldToken.getCount() == 0
                    ? RandomUtil.getMin2Max(fieldToken.getMin(),fieldToken.getMax())
                    :fieldToken.getCount();
            // 当用户的step有值且value长度大于0
            if(fieldNodeContext.getCurrentTokenInfo() != null
                    && fieldNodeContext.getCurrentTokenInfo().getStep()>0
                    && fieldNodeContext.getCurrentTokenInfo().getValue().length>0
                    ){
                int step = fieldNodeContext.getCurrentTokenInfo().getStep();
                String[] value = fieldNodeContext.getCurrentTokenInfo().getValue();
                int valueLength = value.length;
                int count = 0;
                for (int i = 0; i < elementNum; i++) {
                    o.add(value[(count)  % valueLength]);
                    count+= step;
                }
                return o;
            } else {

                if (fieldNodeContext.hasGenericType()) {
                    Type[] actualTypeArguments = fieldNodeContext.getActualTypeArguments();
                    if (actualTypeArguments.length > 1) {
                        throw new IllegalArgumentException("list generate type must be no more than 1");
                    }
                    RandomType executor = mockContext.getExecutor((Class) actualTypeArguments[0]);
                    for (int i = 0; i < elementNum; i++) {
                        // 通过子token规则获取subFiledToken内容

                        if (fieldNodeContext.innerContainerIsInnerType()) {
                            o.add(executor.getRule(fieldNodeContext.getInnerBasicTokens()).apply(mockContext, null));
                        } else {
                            o.add(mockContext.mock((Class<?>) actualTypeArguments[0],fieldNodeContext.getDeclaredField(), fieldNodeContext.getInnerPojoTokens()));
//                            o.add(mockContext.mockWithContext((Class<?>) actualTypeArguments[0], fieldNodeContext));
//                            o.add(fieldNodeContext.);
//                            fieldNodeContext.assignInnerObject(o, mockContext);
                        }
                        // 判断子类型是否是innerType
                    }
                } else {
                    throw new IllegalArgumentException(" list must has type");
                }
                return o;
            }
        }
    }
}
