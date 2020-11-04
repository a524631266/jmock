package com.zhangll.flink;

import com.zhangll.flink.annotation.PojoTokenInfo;
import com.zhangll.flink.model.ASTNode;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.parser.NodeParser;
import com.zhangll.flink.random.ExecutorStore;
import com.zhangll.flink.random.RandomType;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 解析流程
 */
public abstract class MockContext {
    protected static MappingStore mappingStore = new MappingStore();
    protected static NodeParser nodeParser = new NodeParser();
    protected static ExecutorStore executorStore = new ExecutorStore();

    public  Object mock(Class<?> cClass) {

        return this.mock(cClass, null);
    }

    /**
     * 初始化 mappingStore
     * @param cClass
     * @param innerPojoTokens
     * @return
     */
    private FieldNode initMappingStore(Class<?> cClass, Map<String, FieldToken> innerPojoTokens) {
        FieldNode root;
//        if((root = mappingStore.getFieldNode(cClass, mappings))==null){
        root = nodeParser.initNodeTree(cClass, null, innerPojoTokens);
//            mappingStore.setNodeMap(cClass, mappings, root);
//        }
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
            resource = cClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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
     * @param pojoTokens
     * @return
     */
    public Object mock(Class<?> cClass, Map<String, FieldToken> pojoTokens) {
        FieldNode root = null;
        // 暂时先不上mapping
        root = initMappingStore(cClass, pojoTokens);
        // 1. 创建一个对象,不过这个对象是
        Object resource = createObject(cClass, root);
        return doObjectBindField(resource, root);
    }

    public Object mockWithContext(Class currentClass, FieldNode fieldNode){
        Object object = createObject(currentClass, fieldNode);
        return doObjectBindField(object, fieldNode);
    };
}
