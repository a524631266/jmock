package com.zhangll.flink;

import com.zhangll.flink.model.ASTNode;
import com.zhangll.flink.model.ClassNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.random.*;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;

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
        ClassNode root = initNodeTree(cClass);

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
            throw new IllegalArgumentException("参数不可用");
        }
        return doObjectBindField(resource, root);
//        return doMock(cClass, null);
    }

    /**
     * 给 resource 赋值 field
     * @param resource
     * @param classNode
     * @return
     */
    private Object doObjectBindField(Object resource, ClassNode classNode) {
        // 根据classNode初始化变量
        // 给当前节点设置值
        classNode.assignObject(resource, this);
        List<ASTNode> children = classNode.getChildren();
        for (ASTNode child : children) {
            boolean childInnerType = child.isInnerType();
            // 如果子节点中的对象是内置的
            if(childInnerType){
                child.assignObject(resource, this);
            }else{

            }
        }
        return resource;
    }

    /**
     * 初始化一个Node节点树,用来保证数据成功实现内容
     * @param cClass
     * @return
     */
    private ClassNode initNodeTree(Class<?> cClass) {
        ClassNode parent = new ClassNode(cClass , null, initFieldToken(null));
        Field[] declaredFields = cClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 初始化的时候会解析是否为内置类型
            ClassNode childNode = new ClassNode(declaredField.getClass(), declaredField, initFieldToken(declaredField));
            if (childNode.isInnerType()) { // 如果是内置类型就直接添加
                parent.addChild(childNode);
            } else { // 否则递归调用初始化节点
                ClassNode newChildNode = initNodeTree(childNode.getType());
                parent.addChild(newChildNode);
            }
        }
        return parent;
    }

    protected abstract FieldToken initFieldToken(Field field);


}
