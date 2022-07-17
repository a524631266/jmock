package com.zhangll.jmock.core;

import com.zhangll.jmock.core.model.FieldNode;
import com.zhangll.jmock.core.uitl.KeyUtil;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来存储被设置过的 key 与 mapping 映射
 */
public class MappingStore {
    private final Map<String , FieldNode> nodeMap = new HashMap<>();

    public void setNodeMap(String key, FieldNode node) {
        this.nodeMap.put(key, node);
    }

    public void setNodeMap(Class<?> cls, Field field, FieldNode node, Integer deep) {

        setNodeMap(KeyUtil.generateKey(cls, field, deep), node);
    }

    /**
     * 返回不可变对象
     * @return 返回一个map， key：string 类型，node位map
     */
    public Map<String, FieldNode> getNodeMap() {
        return Collections.unmodifiableMap(nodeMap);
    }

    /**
     * 根据key返回对象
     * @param key 查询的key
     * @return 返回node
     */
    public FieldNode getFieldNode(String key) {
        return nodeMap.getOrDefault(key, null);
    }

    public FieldNode getFieldNode(Class<?> cls, Field field, Integer deep) {
        return getFieldNode(KeyUtil.generateKey(cls, field, deep));
    }

}
