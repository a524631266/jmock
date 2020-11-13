package com.zhangll.jmock.core.random;


import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.model.FieldNode;

import java.lang.reflect.Field;

public interface RandomExecutor extends RandomType{

    /**
     *
     * @param target 要被赋值的对象
     * @param context
     * @param fieldNodeContext 词法分析的上下文对象,可以用于查找上下字节点
     * @throws IllegalAccessException
     */
    void updateField(Object target, MockContext context, FieldNode fieldNodeContext);

}
