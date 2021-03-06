package com.zhangll.jmock.core.model;


import com.zhangll.jmock.core.MockContext;
import com.zhangll.jmock.core.random.AbstractRandomExecutor;
import com.zhangll.jmock.core.random.ExecutorStore;
import com.zhangll.jmock.core.random.RandomType;
import com.zhangll.jmock.core.rule.Rule;
import com.zhangll.jmock.core.type.BasicType;
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
    private final ExecutorStore executorStore;
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
     * @param executorStore
     */
    public FieldNode(Class currentClass, Field field,
                     FieldToken currentTokenInfo,
                     Map<String, FieldToken> innerPojoTokens,
                     FieldToken innerBasicTokens, ExecutorStore executorStore) {
        this.currentClass = currentClass;
        this.executorStore = executorStore;
        this.executor = executorStore.getExecutor(currentClass);
//        this.isInnerType = this.executor != null;
        this.isInnerType = executorStore.isInnerType(currentClass);
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

        if (executor != null) {
            ((AbstractRandomExecutor) executor).updateField(target, context, this);
        } else {
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

    /**
     * Array<T>
     * 一般来说就是返回一个范型的list
     * 这里的范型没有做更多的约束，因此只有一个innerBasicTokens可用
     *
     * @return
     */
    @Override
    public List<FieldNode> getGenericFieldNodes() {
        // TODO 先做一个实时获取
        List<FieldNode> result = new ArrayList<>();
        for (Type actualTypeArgument : this.getActualTypeArguments()) {
            FieldNode node = new FieldNode(
                    (Class) actualTypeArgument,
                    null,
                    this.innerBasicTokens,
                    null,
                    null,
                    this.executorStore
                    );
            result.add(node);
        }
        return result;
    }

    @Override
    public FieldNode getComponentContext() {
        FieldNode node = new FieldNode(
                this.getComponentType(),
                null,
                this.innerBasicTokens,
                null,
                null,
                this.executorStore
        );
        return node;
    }


    @Override
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
                    RandomType random = executorStore.getExecutor((Class) actualTypeArguments[i]);
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

            RandomType random = executorStore.getExecutor(componentType);
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
        private int progress = 0;
        private int step = 0;
        private Object preObject;

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
