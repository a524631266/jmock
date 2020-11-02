package com.zhangll.flink.annotation;

import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 定义一个column列表示列名,并且只能用于注解内
 * @author zhangll
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface CompositeTokenMapping {
    /**
     * 表示field名称
     * @return
     */
    String field() default "";
    PojoTokenInfo pojoTokenInfo();
}
