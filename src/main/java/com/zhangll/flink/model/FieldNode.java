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
import java.util.List;

public class FieldNode implements ASTNode{
    final Class currentClass;
    final boolean isInnerType;
    private final RandomType executor;
    // 默认为null
    ASTNode parent;
    private final  Field declaredField;
    final List<ASTNode> children = new ArrayList<>();
    final private FieldToken fieldToken;

    private Class componentType;


    public Class getComponentType() {
        return componentType;
    }

    public FieldNode(Class currentClass, Field field, FieldToken fieldToken) {
        this.currentClass = currentClass;
        this.executor = RandomFactory.getRandom(currentClass);
        this.isInnerType = this.executor != null;
        if(field != null){
            field.setAccessible(true);
            this.componentType = field.getType().getComponentType();
        }
        this.declaredField = field;
        this.fieldToken = fieldToken;

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
        return executor.getRule(fieldToken);
    }

    @Override
    public void assignObject(Object target, MockContext context) {

        ((AbstractRandomExecutor) executor).updateField(target, context,this);

    }

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

    public FieldToken getFieldToken() {
        return fieldToken;
    }

    public void addChild(FieldNode child) {
        children.add(child);
        child.parent = this;
    }
}
