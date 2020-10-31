package com.zhangll.flink.parser;

import com.zhangll.flink.annotation.FieldTokenType;
import com.zhangll.flink.model.FieldNode;
import com.zhangll.flink.model.FieldToken;

import java.lang.reflect.Field;

public class NodeParser {
    /**
     * 初始化一个Node节点树,用来保证数据成功实现内容
     * @param cClass
     * @return
     */
    public FieldNode initNodeTree(Class<?> cClass, Field currentField) {
        FieldNode parent = new FieldNode(cClass , currentField, initFieldToken(currentField));
        Field[] declaredFields = cClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 初始化的时候会解析是否为内置类型
            FieldNode childNode = new FieldNode(declaredField.getType(), declaredField, initFieldToken(declaredField));
            if (childNode.isInnerType()) { // 如果是内置类型就直接添加
                parent.addChild(childNode);
            } else { // 否则递归调用初始化节点
                FieldNode newChildNode = initNodeTree(childNode.getType(), declaredField);
                parent.addChild(newChildNode);
            }
        }
        return parent;
    }


    protected FieldToken initFieldToken(Field field) {
        if(field == null) {
            return null;
        }
        FieldTokenType annotation = field.getAnnotation(FieldTokenType.class);
        FieldToken fieldToken = null;
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
