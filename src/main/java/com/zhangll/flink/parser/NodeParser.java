package com.zhangll.flink.parser;

import com.zhangll.flink.annotation.ContainerTokenInfo;
import com.zhangll.flink.annotation.PojoTokenInfo;
import com.zhangll.flink.annotation.BasicTokenInfo;
import com.zhangll.flink.annotation.TokenMapping;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 目前的版本是用来解析类上的注解作用，如果没有注解就使用默认
 * 优先级
 * 1. 用户传入的配置文件
 * 2. 用户自定义的注解
 * 3. 默认选项
 */
public class NodeParser {
    /**
     * 根据当前的类，获取fieldTokens
     *
     * @param cClass
     * @param currentField
     * @return
     */
    public FieldNode initNodeTree(Class<?> cClass, Field currentField) {
        // 1.获取当前的currentFieldToken
        FieldToken currentBasicToken = getCurrentBasicToken(currentField);
        // 2. 获取innercurrnetToken，一般是在Contain中（id，为列名）
        Map<String, FieldToken> innerPojoTokens = getInnerPojoTokens(currentField);
        FieldToken innerBasicToken = getInnerBasicToken(currentField);
        FieldNode parent = initParentNode(cClass, currentField, currentBasicToken, innerPojoTokens, innerBasicToken);
        // 如果是内置类型，就停止直接返回节点
        if (parent.isInnerType()) {
            return parent;
        }
        Field[] declaredFields = cClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            FieldNode node = initNodeTree(declaredField.getType(), declaredField);
            parent.addChild(node);
        }
        return parent;
    }

    /**
     * 根据Class以及field生活曾一个FieldNode节点
     * @param cClass
     * @param currentField
     * @param currentFieldToken
     * @param innerPojoTokens
     * @param innerBasicToken
     * @return
     */
    private FieldNode initParentNode(Class<?> cClass,
                                     Field currentField,
                                     FieldToken currentFieldToken,
                                     Map<String, FieldToken> innerPojoTokens,
                                     FieldToken innerBasicToken) {
        return new FieldNode(
                cClass,
                currentField,
                currentFieldToken,
                innerPojoTokens,
                innerBasicToken
                );
    }





    /**
     * 根据field获取FieldTokens信息
     * @param field
     * @return
     */
    private static Map<String, FieldToken> getInnerPojoTokens(Field field) {
        ContainerTokenInfo annotation = field.getAnnotation(ContainerTokenInfo.class);
        PojoTokenInfo pojoTokenInfo = annotation.innerPojoType();
        Map<String, FieldToken> result = new HashMap<>();
        TokenMapping[] value = pojoTokenInfo.value();
        for (TokenMapping tokenMapping : value) {
            BasicTokenInfo basicTokenInfo = tokenMapping.basicTokenInfo();
            String fieldName = tokenMapping.field();
            FieldToken fieldToken = new FieldToken.FieldTokenBuilder()
                    .setMin(Integer.valueOf(basicTokenInfo.min()))
                    .setMax(Integer.valueOf(basicTokenInfo.max()))
                    .setDmin(Integer.valueOf(basicTokenInfo.dmin()))
                    .setDmax(Integer.valueOf(basicTokenInfo.dmax()))
                    .setCount(Integer.valueOf(basicTokenInfo.count()))
                    .setValue(basicTokenInfo.value())
                    .setDcount(Integer.valueOf(basicTokenInfo.dcount()))
                    .setStep(Integer.valueOf(basicTokenInfo.step()))
                    .build();
            result.put(fieldName, fieldToken);
        }

        return result;
    }
    /**
     * 根据field获取FieldTokens信息
     * @param field
     * @return
     */
    private static FieldToken getInnerBasicToken(Field field) {
        ContainerTokenInfo annotation = field.getAnnotation(ContainerTokenInfo.class);
        BasicTokenInfo basicTokenInfo = annotation.innerBasicType();
        FieldToken result = new FieldToken.FieldTokenBuilder()
                .setMin(Integer.valueOf(basicTokenInfo.min()))
                .setMax(Integer.valueOf(basicTokenInfo.max()))
                .setDmin(Integer.valueOf(basicTokenInfo.dmin()))
                .setDmax(Integer.valueOf(basicTokenInfo.dmax()))
                .setCount(Integer.valueOf(basicTokenInfo.count()))
                .setValue(basicTokenInfo.value())
                .setDcount(Integer.valueOf(basicTokenInfo.dcount()))
                .setStep(Integer.valueOf(basicTokenInfo.step()))
                .build();
        return result;
    }



    /**
     * 1. 先获取默认的fieldToken
     * 2. 获取注解中fieldToken
     * @param field
     * @return
     */
    protected FieldToken getCurrentBasicToken(Field field) {
        if(field == null) {
            return null;
        }
        BasicTokenInfo basicTokenInfo = field.getAnnotation(BasicTokenInfo.class);
        if(basicTokenInfo == null) {
            return null;
        }
        FieldToken fieldToken = null;
        FieldToken result = new FieldToken.FieldTokenBuilder()
                .setMin(Integer.valueOf(basicTokenInfo.min()))
                .setMax(Integer.valueOf(basicTokenInfo.max()))
                .setDmin(Integer.valueOf(basicTokenInfo.dmin()))
                .setDmax(Integer.valueOf(basicTokenInfo.dmax()))
                .setCount(Integer.valueOf(basicTokenInfo.count()))
                .setValue(basicTokenInfo.value())
                .setDcount(Integer.valueOf(basicTokenInfo.dcount()))
                .setStep(Integer.valueOf(basicTokenInfo.step()))
                .build();
        return fieldToken;
    }

    private FieldToken.FieldTokenBuilder getFieldTokenBuilder(BasicTokenInfo annotation) {
        return new FieldToken.FieldTokenBuilder()
                        .setMin(Integer.valueOf(annotation.min()))
                        .setMax(Integer.valueOf(annotation.max()))
                        .setDmin(Integer.valueOf(annotation.dmin()))
                        .setDmax(Integer.valueOf(annotation.dmax()))
                        .setCount(Integer.valueOf(annotation.count()))
                        .setValue(annotation.value())
                        .setDcount(Integer.valueOf(annotation.dcount()))
                        .setStep(Integer.valueOf(annotation.step()));
    }
}
