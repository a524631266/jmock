package com.zhangll.flink.random;

import com.sun.istack.internal.Nullable;
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
    protected static RulePostProcessor postProcessor = new RulePostProcessor();

    /**
     * 触发计算 1. 需要添加1
     * @param target 要被赋值的对象
     * @param context
     * @param fieldNodeContext 词法分析的上下文对象,可以用于查找上下字节点
     */
    @Override
    public void updateField(Object target, MockContext context, FieldNode fieldNodeContext) {
        // 第一步首先要处理step方法（step优先于其他计算）
        Object compute = handleStep(context, fieldNodeContext);
        // 获取当前上下文中的执行器来计算
        if(compute == null){
            compute = fieldNodeContext.getRule().apply(context, fieldNodeContext);
        }
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

    /**
     *
     * @param context
     * @param fieldNodeContext
     * @return
     */
    @Nullable
    private Object handleStep(MockContext context, FieldNode fieldNodeContext) {
        if(fieldNodeContext.getCurrentTokenInfo() == null){
            return null;
        }
        if(fieldNodeContext.getCurrentTokenInfo().getStep() == 0){
            return null;
        }
        Object o = doHandleStep(context, fieldNodeContext);
        // 坐标 当前step,
        fieldNodeContext.afterState(PostState);
        return o;
    }

    /**
     * 处理step语义 如果可以获取数据那么久
     * @param context
     * @param fieldNodeContext
     * @return
     */
    @Nullable
    protected abstract Object doHandleStep(MockContext context, FieldNode fieldNodeContext);

}

