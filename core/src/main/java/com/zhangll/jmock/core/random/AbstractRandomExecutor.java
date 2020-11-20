package com.zhangll.jmock.core.random;

import javax.annotation.Nullable;

import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.expression.RulePostProcessor;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.type.BasicType;
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
        Object compute = handleCountValue(context, fieldNodeContext);
        if(compute == null) {
            compute = handleStep(context, fieldNodeContext);
        }
        // 获取当前上下文中的执行器来计算
        if(compute == null){
            compute = fieldNodeContext.getRule().apply(context, fieldNodeContext);
        }

        if(compute == null) {
            return;
        }
        Object result = postProcessor.postProcessAfterCompute(compute);
        try {
            fieldNodeContext.getDeclaredField().set(target, convertToCurrentType(fieldNodeContext, result));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(fieldNodeContext.getDeclaredField().getName()
                    + ": 不能被赋值");
        }
//        // 结果集映射没有做好
//        if (BasicType.isArray(result.getClass())) {
//            if (LOG.isDebugEnabled()) {
//                LOG.debug(fieldNodeContext.getDeclaredField().getName());
//                LOG.debug(result.getClass().getName());
//            }
//            try {
//                fieldNodeContext.getDeclaredField().set(target,
//                        BasicType.transWrapperArrayToBasicArray(
//                                fieldNodeContext.getType().getComponentType(),
//                                (Object[]) result));
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(fieldNodeContext.getDeclaredField().getName()
//                        + ": 不能被赋值");
//            }
//        } else {
//            try {
//                fieldNodeContext.getDeclaredField().set(target, convertToCurrentType(result));
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException(fieldNodeContext.getDeclaredField().getName()
//                        + ": 不能被赋值");
//            }
//        }
    }

    /**
     * 转换为当前
     * @param result
     * @return
     */
    protected Object convertToCurrentType(FieldNode fieldNodeContext, Object result) {
        return result;
    }

    /**
     * 处理 value 和 count的
     * @param context
     * @param fieldNodeContext
     * @return
     */
    protected Object handleCountValue(MockContext context, FieldNode fieldNodeContext){
        if(fieldNodeContext.getCurrentTokenInfo() == null){
            return null;
        }
        // 有step则走step
        if(fieldNodeContext.getCurrentTokenInfo().getStep() != 0){
            return null;
        }
        // 没有value = {}
        if(fieldNodeContext.getCurrentTokenInfo().getValue().length  == 0){
            return null;
        }
        return doHandleCountValue(context, fieldNodeContext);
    }

    protected Object doHandleCountValue(MockContext context, FieldNode fieldNodeContext){
        return null;
    };


    ;

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
        Object o = doHandleStep(fieldNodeContext.getCurrentTokenInfo(), fieldNodeContext.getCurrrentStepState());
        // 坐标 更新当前的状态
        fieldNodeContext.updateStepState(o);
        return o;
    }
    /**
     * 处理step语义 基本类型和时间戳类型不一致。
     * @param currentTokenInfo
     * @param currentState
     * @return
     */
    @Nullable
    protected abstract Object doHandleStep(FieldToken currentTokenInfo, FieldNode.StepState currentState);


}

