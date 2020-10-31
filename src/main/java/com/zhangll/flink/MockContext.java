package com.zhangll.flink;

import com.zhangll.flink.model.ASTNode;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 解析流程
 */
public abstract class MockContext {
    protected static MappingStore mappingStore = new MappingStore();
    /**
     * 默认没有rule，使用默认的rule
     * @param cClass
     * @return
     */
    public  Object mock(Class<?> cClass) {
        FieldNode root = initNodeTree(cClass , null);

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

    /**
     * 初始化一个Node节点树,用来保证数据成功实现内容
     * @param cClass
     * @return
     */
    private FieldNode initNodeTree(Class<?> cClass, Field currentField) {
        FieldNode parent = new FieldNode(cClass , currentField, initFieldToken(currentField));
        Field[] declaredFields = cClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 初始化的时候会解析是否为内置类型
            FieldNode childNode = new FieldNode(declaredField.getType(), declaredField, initFieldToken(declaredField));
            if (childNode.isInnerType()) { // 如果是内置类型就直接添加
                parent.addChild(childNode);
            } else { // 否则递归调用初始化节点
                FieldNode newChildNode = initNodeTree(childNode.getType(), declaredField);
                parent.addChild(newChildNode);
            }
        }
        return parent;
    }

    protected abstract FieldToken initFieldToken(Field field);


}
