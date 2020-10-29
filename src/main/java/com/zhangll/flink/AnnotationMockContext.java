package com.zhangll.flink;

import com.zhangll.flink.annotation.FieldTokenType;
import com.zhangll.flink.model.FieldToken;
import com.zhangll.flink.random.RandomFactory;
import com.zhangll.flink.rule.Rule;

import java.lang.reflect.Field;

public class AnnotationMockContext extends MockContext {
    /**
     * 在 注解类中需要关注的是注解，因此，不需要关注path
     * path是用于给配置文件使用的
     *
     * @param personClass
     * @param path
     */
    @Override
    protected void initMapping(Class<?> personClass, String path) {
        Field[] declaredFields = personClass.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            FieldTokenType annotation = declaredField.getAnnotation(FieldTokenType.class);
            if(annotation != null) {

                FieldToken fieldToken = new FieldToken.FieldTokenBuilder()
                        .setMin(Integer.valueOf(annotation.min()))
                        .setMax(Integer.valueOf(annotation.max()))
                        .setDmin(Integer.valueOf(annotation.dmin()))
                        .setDmax(Integer.valueOf(annotation.dmax()))
                        .setCount(Integer.valueOf(annotation.count()))
                        .setValue(annotation.value())
                        .setDcount(Integer.valueOf(annotation.dcount()))
                        .setStep(Integer.valueOf(annotation.step()))
                        .build();
                Rule rule = RandomFactory.getRandom(declaredField.getType()).getRule(fieldToken);
                mappingStore.setRuleMap(personClass, declaredField, rule);
            }
        }

    }
}
