package com.hb.mybatis.assist;

import java.util.HashMap;
import java.util.Map;

/**
 * db元信息
 *
 * @version v0.1, 2022/1/13 23:00, create by huangbiao.
 */
public class TableMeta {

    /**
     * 实体类
     */
    private Class<?> entityClass;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 主键字段名
     */
    private String pkColumnName;

    /**
     * 实体字段 -> 数据库字段映射集合
     */
    private Map<String, String> propertyColumnMap = new HashMap<>(16);

    /**
     * 数据库字段 -> 实体字段映射集合
     */
    private Map<String, String> columnPropertyMap = new HashMap<>(16);

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPkColumnName() {
        return pkColumnName;
    }

    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }

    public Map<String, String> getPropertyColumnMap() {
        return propertyColumnMap;
    }

    public void setPropertyColumnMap(Map<String, String> propertyColumnMap) {
        this.propertyColumnMap = propertyColumnMap;
    }

    public Map<String, String> getColumnPropertyMap() {
        return columnPropertyMap;
    }

    public void setColumnPropertyMap(Map<String, String> columnPropertyMap) {
        this.columnPropertyMap = columnPropertyMap;
    }

    /**
     * 设置实体类字段和数据库字段的映射关系
     *
     * @param propertyName 实体类字段名
     * @param columnName   数据库字段名
     */
    public void setMapping(String propertyName, String columnName) {
        this.propertyColumnMap.put(propertyName, columnName);
        this.columnPropertyMap.put(columnName, propertyName);
    }

}

    