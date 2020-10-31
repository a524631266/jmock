package com.zhangll.flink;

import com.zhangll.flink.model.ASTNode;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.parser.NodeParser;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 解析流程
 */
public abstract class MockContext {
    protected static MappingStore mappingStore = new MappingStore();
    protected static NodeParser nodeParser = new NodeParser();
    /**
     * 默认没有rule，使用默认的rule
     * @param cClass
     * @return
     */
    public  Object mock(Class<?> cClass) {
        FieldNode root = null;
        if((root = mappingStore.getFieldNode(cClass, null))==null){
            root = nodeParser.initNodeTree(cClass , null);
            mappingStore.setRuleMap(cClass, null, root);
        }
        // 1. 创建一个对象,不过这个对象是
        Object resource = null;
        try {
            resource = root.getType().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if(resource == null){
            throw new IllegalArgumentException(cClass.getCanonicalName() + ": 无法实例化");
        }
        return doObjectBindField(resource, root);
//        return doMock(cClass, null);
    }

    /**
     * 给 resource 赋值 field
     * @param resource
     * @param fieldNode
     * @return
     */
    private Object doObjectBindField(Object resource, FieldNode fieldNode) {
        // 根据classNode初始化变量
        // 给当前节点设置值
        fieldNode.assignObject(resource, this);
        List<ASTNode> children = fieldNode.getChildren();
        for (ASTNode child : children) {
            boolean childInnerType = child.isInnerType();
            // 如果子节点中的对象是内置的
            if(childInnerType){
                child.assignObject(resource, this);
            }else{
                Object mock = this.mock(child.getType());
                child.assignObject(resource ,mock);
//                return mock;
            }
        }
        return resource;
    }


}
