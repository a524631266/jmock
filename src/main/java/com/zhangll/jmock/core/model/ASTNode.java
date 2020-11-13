package com.zhangll.jmock.core.model;

import com.zhangll.flink.MockContext;

import java.util.List;

public interface ASTNode {
    ASTNode getParent();
    List<ASTNode> getChildren();
    Class getType();
    boolean isInnerType();

    /**
     * 如果是内置对象，那么就根据内置执行器执行计算
     * @param target
     * @param context
     */
    void assignInnerObject(Object target, MockContext context);
    void swap(Object target, Object source);

}
