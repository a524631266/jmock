package com.zhangll.jmock.core.ext;

import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.random.AbstractRandomExecutor;
import com.zhangll.jmock.core.rule.Rule;
import com.zhangll.jmock.core.uitl.RandomUtil;

import javax.annotation.Nullable;

public class EnumRandomExecutor extends AbstractRandomExecutor {
    private EnumRule enumDefaultRule = new EnumRule(
            new FieldToken.FieldTokenBuilder()
                    .setCount(1)
            .build()
    );
    @Nullable
    @Override
    protected Object doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState) {
        return null;
    }

    @Override
    public boolean isCurrentType(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public Rule getDefaultRule() {
        return enumDefaultRule;
    }

    @Override
    public Rule getRule(FieldToken fieldToken) {
        return new EnumRule(fieldToken);
    }

    class EnumRule implements Rule {

        private final FieldToken fieldToken;

        public EnumRule(FieldToken fieldToken) {
            this.fieldToken = fieldToken;
        }

        @Override
        public Object apply(MockContext mockContext, FieldNode fieldNodeContext) {
            Class type = fieldNodeContext.getType();
            Object[] enumConstants = type.getEnumConstants();
            int max = enumConstants.length -1;
            if(max < 0){
                return null;
            }
            Integer randomId = RandomUtil.getMin2Max(0, max);
            return enumConstants[randomId];
        }
    }
}
