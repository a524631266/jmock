package com.zhangll.flink.random;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.expression.RulePostProcessor;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.type.BasicType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 在Mock体系中属于执行器的范围
 */
public abstract class AbstractRandomExecutor implements RandomExecutor {
    public static Logger LOG = LoggerFactory.getLogger(AbstractRandomExecutor.class);
    protected RulePostProcessor postProcessor = new RulePostProcessor();

    @Override
    public void updateField(Object target, MockContext context, FieldNode fieldNodeContext) {
        // 获取当前上下文中的执行器来计算
        Object compute = fieldNodeContext.getRule().apply(context, fieldNodeContext);
        Object result = postProcessor.postProcessAfterCompute(compute);
        if (BasicType.isArray(result.getClass())) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(fieldNodeContext.getDeclaredField().getName());
                LOG.debug(result.getClass().getName());
            }
            try {
                fieldNodeContext.getDeclaredField().set(target,
                        BasicType.transWrapperArrayToBasicArray(
                                fieldNodeContext.getType().getComponentType(),
                                (Object[]) result));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(fieldNodeContext.getDeclaredField().getName()
                        + ": 不能被赋值");
            }
        } else {
            try {
                fieldNodeContext.getDeclaredField().set(target, result);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(fieldNodeContext.getDeclaredField().getName()
                        + ": 不能被赋值");
            }
        }
    }

//    compute

}

