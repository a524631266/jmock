package com.zhangll.flink.model;

import com.zhangll.flink.MockContext;
import com.zhangll.flink.random.AbstractRandomExecutor;
import com.zhangll.flink.random.RandomExecutorFactory;
import com.zhangll.flink.random.RandomType;
import com.zhangll.flink.rule.Rule;
import com.zhangll.flink.type.BasicType;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
//    private Map<String, FieldToken> fieldTokens = new HashMap<>();
    /**
     * 默认为null
      */
    ASTNode parent;
    private final  Field declaredField;
    final List<ASTNode> children = new ArrayList<>();
    /**
     * 当前节点的fieldToken, 一般为节点当前设置的FieldTokenInfo内容
      */
    final private FieldToken currentTokenInfo;
    // 当前节点的 或者容器内部的
    private Map<String, FieldToken> innerPojoTokens = new HashMap<>(1);
    private FieldToken innerBasicTokens;

    // 数组类型的类型
    private Class componentType;
    // 泛型参数实际类型
    private Type[] actualTypeArguments;

    public final StepState stepState;

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
        this.executor = RandomExecutorFactory.getRandom(currentClass);
        this.isInnerType = this.executor != null;
        if(field != null){
            field.setAccessible(true);
            this.componentType = field.getType().getComponentType();

            Type genericType = field.getGenericType();
            if(genericType instanceof ParameterizedType){
                this.actualTypeArguments =(Type[]) ((ParameterizedType) genericType).getActualTypeArguments();
            }
        }
        this.declaredField = field;
        this.currentTokenInfo = currentTokenInfo;

        this.innerPojoTokens = innerPojoTokens;
        this.innerBasicTokens = innerBasicTokens;
        if(currentTokenInfo == null){
            this.stepState = new StepState(0, 0);
        }else{
            this.stepState = new StepState(0, currentTokenInfo.getStep());
        }
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


    public Rule getRule(){
        return executor.getRule(currentTokenInfo);
    }

    @Override
    public void assignInnerObject(Object target, MockContext context) {
        if(isInnerType){
            ((AbstractRandomExecutor) executor).updateField(target, context,this);
        }else{
            Object source = context.mockWithContext(this.currentClass, this);
            this.swap(target, source);
        }

    }

    /**
     *
     * @param target 被赋值的对象
     * @param source 生成的新对象
     */
    @SneakyThrows
    @Override
    public void swap(Object target, Object source) {
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
    public boolean hasGenericType() {
        return actualTypeArguments!=null && actualTypeArguments.length > 0;
    }

    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    public FieldToken getInnerBasicTokens() {
        return innerBasicTokens;
    }

    /**
     * 是容器[list] 并且genrate范型是内置
     */
    public boolean innerContainerIsInnerType(){
        if(componentType == null && actualTypeArguments == null){
            return false;
        }
        if(actualTypeArguments !=null){
            if(actualTypeArguments.length > 1){
                return false;
            }else{
                boolean isInner = false;
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    RandomType random = RandomExecutorFactory.getRandom((Class) actualTypeArguments[i]);
                    if (random != null) {
                        isInner = true;
                        break;
                    }
                }
                return isInner;
            }
        }

        if(componentType != null){
            boolean isInner = false;

            RandomType random = RandomExecutorFactory.getRandom(componentType);
            if (random != null) {
                isInner = true;

            }

            return isInner;

        }
        return false;
    }
    public Map<String, FieldToken> getInnerPojoTokens() {
        return innerPojoTokens;
    }

    public void updateStepState(Object object) {
        stepState.updateState(object);
    }

    public StepState getCurrrentStepState(){
        return stepState;
    }
    @Getter
    @ToString
    public class StepState {
        // 进度
        int progress = 0;
        int step = 0;
        Object preObject;

        public StepState(int progress, int step) {
            this.progress = progress;
            this.step = step;
        }

        protected void updateState(Object object) {
            this.preObject = object;
            if(object == null) {
                return;
            }
            // 每次刷新都会更新一下进度，进度为 默认+2操作
            progress += step;
        }
    }
}
