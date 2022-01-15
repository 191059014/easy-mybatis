package com.hb.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列注解
 *
 * @version v0.1, 2022/1/13 14:52, create by huangbiao.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbField {

    /**
     * 列名
     */
    String value() default "";

    /**
     * 是否是数据库主键
     */
    boolean isPk() default false;

}
