package com.zhangll.flink.model;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.random.AbstractComplexRandom;
import com.zhangll.flink.random.AbstractSimpleRandom;
import com.zhangll.flink.random.RandomFactory;
import com.zhangll.flink.random.RandomType;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ClassNode implements ASTNode{
    final Class currentClass;
    final boolean isInnerType;
    private final RandomType executor;
    // 默认为null
    ASTNode parent;
    private final  Field declaredField;
    final List<ASTNode> children = new ArrayList<>();
    final private FieldToken fieldToken;


    public ClassNode(Class currentClass, Field field, FieldToken fieldToken) {
        this.currentClass = currentClass;
        this.executor = RandomFactory.getRandom(currentClass);
        this.isInnerType = this.executor != null;
        if(field!= null){
            field.setAccessible(true);
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

    private Rule getRule(){
        return executor.getRule(fieldToken);
    }
    public void assignObject(Object object, MockContext context) {
        if(executor instanceof AbstractSimpleRandom){
            ((AbstractSimpleRandom) executor).updateField(object, declaredField, getRule());
        }else if(executor instanceof AbstractComplexRandom){
            ((AbstractComplexRandom) executor).updateField(object, declaredField, getRule(), context);
        }
    }

    @Override
    public boolean isInnerType() {
        return isInnerType;
    }

    public void addChild(ClassNode child) {
        children.add(child);
        child.parent = this;
    }
}
