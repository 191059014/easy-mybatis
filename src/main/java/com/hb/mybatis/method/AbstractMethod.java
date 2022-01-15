package com.hb.mybatis.method;

import com.hb.mybatis.assist.ISqlSegment;
import com.hb.mybatis.enums.SqlMethod;

/**
 * 抽象方法
 *
 * @version v0.1, 2022/1/13 22:38, create by huangbiao.
 */
public abstract class AbstractMethod implements ISqlSegment {

    /**
     * 实体类
     */
    protected Class<?> entityClass;

    /**
     * sql方法
     */
    protected final SqlMethod sqlMethod;

    protected AbstractMethod(Class<?> entityClass, SqlMethod sqlMethod) {
        this.entityClass = entityClass;
        this.sqlMethod = sqlMethod;
    }

}

    