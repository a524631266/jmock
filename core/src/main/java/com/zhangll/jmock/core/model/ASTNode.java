package com.zhangll.jmock.core.model;



import com.zhangll.jmock.core.MockContext;

import java.lang.reflect.Field;
import java.util.List;

public interface ASTNode {
    ASTNode getParent();
    List<ASTNode> getChildren();
    Class getType();

    Field getDeclaredField();

    boolean isInnerType();

    /**
     * 如果是内置对象，那么就根据内置执行器执行计算
     * @param target
     * @param context
     */
    void assignInnerObject(Object target, MockContext context);
    void swap(Object target, Object source);

    /**
     * 获取当前Field节点树中的范型所表示的tokens
     * @return
     */
    List<FieldNode> getGenericFieldNodes();


    FieldNode getComponentContext();
}
