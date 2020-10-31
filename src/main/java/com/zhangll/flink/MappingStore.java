package com.zhangll.flink;

import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.uitl.KeyUtil;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来存储被设置过的 key 与 mapping 映射
 */
public class MappingStore {
    private Map<String , FieldNode> nodeMap = new HashMap<>();

    public void setRuleMap(String key, FieldNode node) {
        this.nodeMap.put(key, node);
    }

    public void setRuleMap(Class cls, Field field, FieldNode node) {

        setRuleMap(KeyUtil.generateKey(cls, field), node);
    }

    /**
     * 返回不可变对象
     * @return
     */
    public Map<String, FieldNode> getNodeMap() {
        return Collections.unmodifiableMap(nodeMap);
    }

    /**
     * 根据key返回对象
     * @param key
     * @return
     */
    public FieldNode getFieldNode(String key) {
        FieldNode orDefault = nodeMap.getOrDefault(key, null);
        return orDefault;
    }

    public FieldNode getFieldNode(Class cls, Field field) {
        return getFieldNode(KeyUtil.generateKey(cls, field));
    }
}
