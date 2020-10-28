package com.zhangll.flink.annotation;

import java.lang.annotation.*;

/**
 * 用于支持fileld类型
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldTokenType {
    String min() default "0";
    String max() default "0";
    String count() default "0";
    String dmin() default "0";
    String dmax() default "0";
    String dcount() default "0";
    String step() default "0";
    String[] value() default {};
//    FieldTokenType subFieldToken() default @FieldTokenType;
}
