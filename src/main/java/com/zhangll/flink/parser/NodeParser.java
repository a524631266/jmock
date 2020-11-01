package com.zhangll.flink.parser;

import com.zhangll.flink.annotation.FieldTokenType;
import com.zhangll.flink.annotation.InnerTokens;
import com.zhangll.flink.annotation.TokenMapping;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.random.RandomFactory;
import com.zhangll.flink.random.RandomType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class NodeParser {
    /**
     * 初始化一个Node节点树,用来保证数据成功实现内容
     * 1. 如果有一个json字符串 + class 这样可以对当前对象初始化节点吗?
     *
     * @param cClass
     * @return
     */
    public FieldNode initNodeTree(Class<?> cClass, Field currentField, FieldToken outerFieldToken) {
        Map<String, FieldToken> tokenMap = getTokenMap(cClass);
        FieldNode parent = new FieldNode(
                cClass,
                currentField,
                outerFieldToken == null ?
                        initFieldToken(currentField) : outerFieldToken);

        if (parent.isInnerType()) {
            return parent;
        }
        Field[] declaredFields = cClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 初始化的时候会解析是否为内置类型
            FieldNode childNode = new FieldNode(
                    declaredField.getType(),
                    declaredField,
                    initFieldToken(declaredField));
            if (childNode.isInnerType()) { // 如果是内置类型就直接添加
                parent.addChild(childNode);
            } else { // 否则递归调用初始化节点
                FieldNode newChildNode = initNodeTree(childNode.getType(), declaredField,
                        tokenMap.get(childNode.getDeclaredField().getName()));
                parent.addChild(newChildNode);
            }
        }
        return parent;
    }

    protected Map<String, FieldToken> getTokenMap(Field field){
        InnerTokens annotation = field.getAnnotation(InnerTokens.class);
        Map<String, FieldToken> result = getStringFieldTokenMap(annotation);
        return result;
    }
    protected static Map<String, FieldToken> getTokenMap(Class cClass){
        InnerTokens annotation = (InnerTokens) cClass.getAnnotation(InnerTokens.class);
        Map<String, FieldToken> result = getStringFieldTokenMap(annotation);
        return result;
    }

    private static Map<String, FieldToken> getStringFieldTokenMap(InnerTokens annotation) {
        Map<String, FieldToken> result = new HashMap<>();
        if(annotation!=null){
            TokenMapping[] value = annotation.value();
            for (TokenMapping tokenMapping : value) {
                FieldTokenType fieldTokenType = tokenMapping.fieldTokenType();
                String fieldName = tokenMapping.field();
                FieldToken fieldToken = new FieldToken.FieldTokenBuilder()
                        .setMin(Integer.valueOf(fieldTokenType.min()))
                        .setMax(Integer.valueOf(fieldTokenType.max()))
                        .setDmin(Integer.valueOf(fieldTokenType.dmin()))
                        .setDmax(Integer.valueOf(fieldTokenType.dmax()))
                        .setCount(Integer.valueOf(fieldTokenType.count()))
                        .setValue(fieldTokenType.value())
                        .setDcount(Integer.valueOf(fieldTokenType.dcount()))
                        .setStep(Integer.valueOf(fieldTokenType.step()))
                        .build();
                result.put(fieldName, fieldToken);
            }
        }
        return result;
    }


    /**
     * 1. 先获取默认的fieldToken
     * 2. 获取注解中fieldToken
     * @param field
     * @return
     */
    protected FieldToken initFieldToken(Field field) {
        if(field == null) {
            return null;
        }
        FieldTokenType annotation = field.getAnnotation(FieldTokenType.class);
        RandomType random = RandomFactory.getRandom(field.getType());
        FieldToken fieldToken = null;
        if(random!=null){
            fieldToken = random.
        }
        if(annotation != null) {
            fieldToken = new FieldToken.FieldTokenBuilder()
                    .setMin(Integer.valueOf(annotation.min()))
                    .setMax(Integer.valueOf(annotation.max()))
                    .setDmin(Integer.valueOf(annotation.dmin()))
                    .setDmax(Integer.valueOf(annotation.dmax()))
                    .setCount(Integer.valueOf(annotation.count()))
                    .setValue(annotation.value())
                    .setDcount(Integer.valueOf(annotation.dcount()))
                    .setStep(Integer.valueOf(annotation.step()))
                    .build();

        }
        return fieldToken;
    }
}
