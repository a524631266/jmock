package com.zhangll.jmock.core.annotation;

import java.lang.annotation.*;

/**
 * 定义一个column列表示列名,并且只能用于注解内
 * @author zhangll
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface TokenMapping {
    /**
     * id序列
     * @return
     */
    boolean id() default false;

    /**
     * 表示field名称
     * @return
     */
    String field() default "";
    BasicTokenInfo basicTokenInfo();
}
