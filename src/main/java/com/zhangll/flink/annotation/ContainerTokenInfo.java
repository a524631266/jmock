package com.zhangll.flink.annotation;

import java.lang.annotation.*;

/**
 * 表示放置容器的类型，比如 ArrayList<ElementType>,Set<ElementType>,String[], ElementType[]
 * cyclic annotation element type
 * @author zhangll
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface ContainerTokenInfo {
    BasicTokenInfo innerBasicType() default @BasicTokenInfo;
    PojoTokenInfo innerPojoType() default @PojoTokenInfo;
}
