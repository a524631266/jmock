package com.zhangll.flink;

import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.uitl.KeyUtil;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 用来存储被设置过的 key 与 mapping 映射
 */
public class MappingStore {
    private Map<String , Rule> ruleMap = new HashMap<>();

    public void setRuleMap(String key, Rule rule) {
        this.ruleMap.put(key, rule);
    }

    public void setRuleMap(Class cls, Field field, Rule rule) {

        setRuleMap(KeyUtil.generateKey(cls, field), rule);
    }

    /**
     * 返回不可变对象
     * @return
     */
    public Map<String, Rule> getRuleMap() {
        return Collections.unmodifiableMap(ruleMap);
    }

    /**
     * 根据key返回对象
     * @param key
     * @return
     */
    public Rule getRule(String key) {
        Rule orDefault = ruleMap.getOrDefault(key, null);
        return orDefault;
    }

    public Rule getRule(Class cls, Field field) {
        return getRule(KeyUtil.generateKey(cls, field));
    }
}
