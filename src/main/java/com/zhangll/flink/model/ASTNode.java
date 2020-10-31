package com.zhangll.flink.model;

import com.zhangll.flink.MockContext;

import java.util.List;

public interface ASTNode {
    ASTNode getParent();
    List<ASTNode> getChildren();
    Class getType();
    boolean isInnerType();
    void assignObject(Object object, MockContext context);
}
