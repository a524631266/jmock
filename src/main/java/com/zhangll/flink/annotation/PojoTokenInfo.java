package com.zhangll.flink.annotation;

import java.lang.annotation.*;

/**
 * 用于支持field类型
 * 不要放在class上，因为这样会导致死循环特性！！
 * @author zhangll
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PojoTokenInfo {
    /**
     * The name of the mapping ,
     * 当 为空字符串，默认表示为当前类的全限定名
     * @return
     */
    String id() default "";
    TokenMapping[]  value() default {};
}
