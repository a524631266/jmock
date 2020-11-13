package com.zhangll.jmock.core;


import com.zhangll.jmock.core.model.ASTNode;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.parser.NodeParser;
import com.zhangll.jmock.core.random.ExecutorStore;
import com.zhangll.jmock.core.random.RandomType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 解析流程
 */
public abstract class MockContext {
    protected MappingStore mappingStore = new MappingStore();
    protected static NodeParser nodeParser = new NodeParser();
    protected ExecutorStore executorStore = new ExecutorStore();

    public  Object mock(Class<?> cClass) {

        return this.mock(cClass, null, null);
    }

    /**
     * 初始化 mappingStore
     * @param cClass
     * @param innerPojoTokens
     * @return
     */
    private FieldNode initMappingStore(Class<?> cClass, Field ownField, Map<String, FieldToken> innerPojoTokens) {
        FieldNode root;
        if((root = mappingStore.getFieldNode(cClass ,ownField ))==null){
            root = nodeParser.initNodeTree(cClass, null, innerPojoTokens);
            mappingStore.setNodeMap(cClass, ownField, root);
        }
        return root;
    }

    /**
     * 创建一个对象
     * @param cClass
     * @param root
     * @return
     */
    private Object createObject(Class<?> cClass, FieldNode root) {
        Object resource = null;
        try {
//            resource = root.getType().newInstance();
//            resource = cClass.newInstance();
            // 一般使用默认的构造器构造方法
            Constructor<?>[] declaredConstructors = cClass.getDeclaredConstructors();
            for (Constructor<?> declaredConstructor : declaredConstructors) {
                if(declaredConstructor.getParameterCount() == 0){
                    declaredConstructor.setAccessible(true);
                    resource = declaredConstructor.newInstance();
                    break;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch ( InvocationTargetException e ) {
            e.printStackTrace();
        }
        if(resource == null){
            throw new IllegalArgumentException(cClass.getCanonicalName() + ": 无法实例化");
        }
        return resource;
    }

    /**
     * 给 target 赋值 field,
     * 对这颗树中的节点进行遍历
     * @param target
     * @param fieldNode
     * @return
     */
    private Object doObjectBindField(Object target, FieldNode fieldNode) {
        // 根据classNode初始化变量
        // 给当前节点设置值, 根据之前的状态来
        List<ASTNode> children = fieldNode.getChildren();
        for (ASTNode child : children) {
            child.assignInnerObject(target, this);
        }
        return target;
    }

    public RandomType getExecutor(Class cClass){
        RandomType executor = executorStore.getExecutor(cClass);
        return executor;
    }

    /**
     * 一般为 mock对象
     * @param cClass
     * @param ownField 当前所属域
     * @param pojoTokens
     * @return
     */
    public Object mock(Class<?> cClass, Field ownField ,Map<String, FieldToken> pojoTokens) {
        FieldNode root = null;
        // 暂时先不上mapping
        root = initMappingStore(cClass, ownField, pojoTokens);
        // 1. 创建一个对象,不过这个对象是
        Object resource = createObject(cClass, root);
        return doObjectBindField(resource, root);
    }

    public Object mockWithContext(Class currentClass, FieldNode fieldNode){
        Object object = createObject(currentClass, fieldNode);
        return doObjectBindField(object, fieldNode);
    };
}
