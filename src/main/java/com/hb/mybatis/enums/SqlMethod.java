package com.hb.mybatis.enums;

import com.hb.mybatis.assist.ISqlSegment;

/**
 * sql方法
 *
 * @version v0.1, 2022/1/13 21:55, create by huangbiao.
 */
public enum SqlMethod implements ISqlSegment {

    INSERT("新增", "insert into %s (%s) values (%s)"),
    DELETE("删除", "delete from %s "),
    UPDATE("修改", "update %s set %s "),
    SELECT_LIST("查询列表", "select %s from %s "),
    SELECT_COUNT("查询总条数", "select count(1) from %s "),;

    /**
     * 描述
     */
    private final String desc;

    /**
     * sql
     */
    private final String sql;

    SqlMethod(String desc, String sql) {
        this.desc = desc;
        this.sql = sql;
    }

    /**
     * 获取sql片段
     *
     * @return sql片段
     */
    @Override
    public String getSqlSegment() {
        return sql;
    }

}
