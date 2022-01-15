package com.hb.mybatis.util;

import com.hb.mybatis.assist.Constants;

/**
 * sql脚本工具类
 *
 * @version v0.1, 2022/1/13 22:22, create by huangbiao.
 */
public class SqlScriptUtils implements Constants {

    /**
     * 装饰属性
     *
     * @param propertyName 属性名
     * @return mybatis取值的格式
     */
    public static String decorateParameter(String propertyName) {
        return String.format(PROPERTY_FORMAT, propertyName);
    }

    /**
     * 装饰数据库字段
     *
     * @param columnName 列名
     * @return mybatis取值的格式
     */
    public static String decorateColumn(String columnName) {
        return String.format(COLUMN_FORMAT, columnName);
    }

}

    