package com.zhangll.jmock.core;


import com.zhangll.jmock.core.model.ASTNode;
import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.model.FieldToken;
import com.zhangll.jmock.core.parser.NodeParser;
import com.zhangll.jmock.core.random.AbstractRandomExecutor;
import com.zhangll.jmock.core.random.ExecutorStore;
import com.zhangll.jmock.core.random.RandomType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 解析流程
 */
public abstract class MockContext {
    private final  static Logger LOG = LoggerFactory.getLogger(MockContext.class);
    protected MappingStore mappingStore = new MappingStore();
    protected static NodeParser nodeParser = new NodeParser();
    protected ExecutorStore executorStore = new ExecutorStore();

    public <T> T mock(Class<T> cClass) {
        return (T) this.mock(cClass, null, null);
    }

    /**
     * 初始化 mappingStore
     * @param cClass
     * @param innerPojoTokens
     * @return
     */
    private FieldNode initMappingStore(Class<?> cClass, Field containerField, Map<String, FieldToken> innerPojoTokens) {
        FieldNode root;
        if((root = mappingStore.getFieldNode(cClass ,containerField ))==null){
            root = nodeParser.initNodeTree(cClass, null, innerPojoTokens);
            mappingStore.setNodeMap(cClass, containerField, root);
        }
        return root;
    }

    /**
     * 创建一个空对象
     * @param cClass
     * @param root
     * @param pojoTokens
     * @return
     */
    protected Object createObject(Class<?> cClass, FieldNode root, Map<String, FieldToken> pojoTokens) {
        Object resource = null;
        try {
//            resource = root.getType().newInstance();
//            resource = cClass.newInstance();
            // 一般使用默认的构造器构造方法
            resource = getInnerClassObject(cClass, root, pojoTokens);
            if(resource == null){
                Constructor<?>[] declaredConstructors = cClass.getDeclaredConstructors();
                for (Constructor<?> declaredConstructor : declaredConstructors) {
                    if(declaredConstructor.getParameterCount() == 0){
                        declaredConstructor.setAccessible(true);
                        resource = declaredConstructor.newInstance();
                        break;
                    }
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
            LOG.debug(cClass.getCanonicalName() + ": 无法实例化");
//            throw new IllegalArgumentException(cClass.getCanonicalName() + ": 无法实例化");
        }
        return resource;
    }

    /**
     * 不断闭环
     *
     * @param cClass
     * @param root
     * @param pojoTokens
     * @return
     */
    private Object getInnerClassObject(Class<?> cClass, FieldNode root, Map<String, FieldToken> pojoTokens) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> enclosingClass = cClass.getEnclosingClass();
        if (enclosingClass != null) {
            // 单个对象. 这里如何判断是否停止mock,是根据什么来判断
//            Object enCloseObject = createObject(enclosingClass, root, pojoTokens);
            // container对象
            Object enCloseObject = mock(enclosingClass, root==null || root.getParent() == null? null: root.getParent().getDeclaredField(), pojoTokens);
            Constructor<?> constructor = cClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            if(constructor != null){
                return constructor.newInstance(enCloseObject);
            }
        }
        return null;
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
        if(fieldNode == null){
            return null;
        }
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
     * @param containerField 当前类所属容器的field
     * @param pojoTokens 映射, 一般是所属类名
     * @return
     */
    public Object mock(Class<?> cClass, Field containerField ,Map<String, FieldToken> pojoTokens) {
        FieldNode root = null;
        // 暂时先不上mapping
        root = initMappingStore(cClass, containerField, pojoTokens);
        // 1. 创建一个对象,不过这个对象是
        Object resource = createObject(cClass, root, pojoTokens);
        return doObjectBindField(resource, root);
    }

    public Object mockWithContext(Class currentClass, FieldNode fieldNode){
        Object object = createObject(currentClass, fieldNode, null);
        return doObjectBindField(object, fieldNode);
    };
}
