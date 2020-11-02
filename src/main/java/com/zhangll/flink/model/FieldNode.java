package com.zhangll.flink.model;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.random.AbstractRandomExecutor;
import com.zhangll.flink.random.RandomFactory;
import com.zhangll.flink.random.RandomType;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析树节点包含多个重要对象
 * 1.
 */
public class FieldNode implements ASTNode{
    final Class currentClass;
    final boolean isInnerType;
    private final RandomType executor;
    private Map<String, FieldToken> fieldTokens = new HashMap<>();
    // 默认为null
    ASTNode parent;
    private final  Field declaredField;
    final List<ASTNode> children = new ArrayList<>();
    // 当前节点的fieldToken, 一般为节点当前设置的FieldTokenInfo内容
    final private FieldToken currentTokenInfo;
    // 当前节点的 或者容器内部的
    private Map<String, FieldToken> innerPojoTokens = new HashMap<>(1);
    private FieldToken innerBasicTokens;


    private Class componentType;


    public Class getComponentType() {
        return componentType;
    }

//    public FieldNode(Class currentClass, Field field, FieldToken currentTokenInfo){
//        this(currentClass, field, currentTokenInfo, null,null);
//    }

    /**
     * 解析获取当前的tokens info信息
     * @param currentClass
     * @param field
     * @param currentTokenInfo
     * @param innerPojoTokens
     * @param innerBasicTokens
     */
    public FieldNode(Class currentClass, Field field,
                     FieldToken currentTokenInfo,
                     Map<String, FieldToken> innerPojoTokens,
                     FieldToken innerBasicTokens) {
        this.currentClass = currentClass;
        this.executor = RandomFactory.getRandom(currentClass);
        this.isInnerType = this.executor != null;
        if(field != null){
            field.setAccessible(true);
            this.componentType = field.getType().getComponentType();
        }
        this.declaredField = field;
        this.currentTokenInfo = currentTokenInfo;

        if(fieldTokens!=null){
            this.fieldTokens = fieldTokens;
        }
        this.innerPojoTokens = innerPojoTokens;
        this.innerBasicTokens = innerBasicTokens;
    }


    @Override
    public ASTNode getParent() {
        return parent;
    }

    @Override
    public List<ASTNode> getChildren() {
        return children;
    }

    @Override
    public Class getType() {
        return currentClass;
    }

    public RandomType getExecutor() {
        return executor;
    }

    public Rule getRule(){
        return executor.getRule(currentTokenInfo);
    }

    @Override
    public void assignInnerObject(Object target, MockContext context) {

        ((AbstractRandomExecutor) executor).updateField(target, context,this);

    }

    /**
     *
     * @param target 被赋值的对象
     * @param source 生成的新对象
     */
    @SneakyThrows
    @Override
    public void assignObject(Object target, Object source) {
        declaredField.set(target, source);
    }

    public Field getDeclaredField() {
        return declaredField;
    }

    @Override
    public boolean isInnerType() {
        return isInnerType;
    }

    public boolean isArray(){
        return BasicType.isArray(currentClass);
    }

    public FieldToken getCurrentTokenInfo() {
        return currentTokenInfo;
    }

    public void addChild(FieldNode child) {
        children.add(child);
        child.parent = this;
    }
}
