package com.hb.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表信息的注解
 *
 * @author Mr.Huang
 * @version v0.1, 2022/1/13 14:52, create by huangbiao.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

    /**
     * 表名
     *
     * @return 表名
     */
    String value();

}
