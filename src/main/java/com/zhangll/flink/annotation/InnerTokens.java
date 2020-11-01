package com.zhangll.flink.annotation;

import java.lang.annotation.*;

/**
 * 用于支持fileld类型
 *
 * @author zhangll
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface InnerTokens {
    /**
     * The name of the mapping
     * @return
     */
    String id() default "";
    TokenMapping[] value() default {};
}
