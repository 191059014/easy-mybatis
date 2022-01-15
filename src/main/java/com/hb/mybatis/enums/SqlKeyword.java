package com.hb.mybatis.enums;

import com.hb.mybatis.assist.ISqlSegment;

/**
 * sql关键字
 *
 * @version v0.1, 2022/1/13 22:06, create by huangbiao.
 */
public enum SqlKeyword implements ISqlSegment {

    WHERE("where "),
    AND("and "),
    OR("or "),
    EQUAL("%s = %s "),
    NOT_EQUAL("%s <> %s "),
    MAX_THAN("%s > %s "),
    MAX_EQUAL_THAN("%s >= %s "),
    MIN_THAN("%s < %s "),
    MIN_EQUAL_THAN("%s <= %s "),
    BETWEEN("%s between %s and %s "),
    IS_NULL("%s is null "),
    IS_NOT_NULL("%s is not null "),
    IN("%s in (%s) "),
    NOT_IN("%s not in (%s) "),
    LIKE("%s like concat(%s,'%%') "),
    ORDER_BY("order by %s "),
    GROUP_BY("group by %s "),
    LIMIT("limit %s,%s "),;

    /**
     * 关键字
     */
    private final String sql;

    SqlKeyword(String sql) {
        this.sql = sql;
    }

    /**
     * 获取sql片段
     *
     * @return sql片段
     */
    @Override
    public String getSqlSegment() {
        return this.sql;
    }

}
