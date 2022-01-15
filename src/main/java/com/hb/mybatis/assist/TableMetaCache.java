package com.hb.mybatis.assist;

import com.hb.mybatis.annotation.DbField;
import com.hb.mybatis.annotation.Entity;
import com.hb.mybatis.util.ReflectUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * db元数据信息缓存
 *
 * @version v0.1, 2022/1/13 22:57, create by huangbiao.
 */
public class TableMetaCache {

    /**
     * 单例
     */
    private static final TableMetaCache CACHE = new TableMetaCache();

    /**
     * db元信息集合
     */
    private final Map<Class<?>, TableMeta> dbMetas = new HashMap<>();

    /**
     * 将数据库实体类元信息放入缓存
     *
     * @param entityClass 数据库实体类
     */
    public static void put(Class<?> entityClass) {
        TableMeta tableMeta = new TableMeta();
        tableMeta.setEntityClass(entityClass);
        // 获取表名
        Entity entity = entityClass.getAnnotation(Entity.class);
        Assert.notNull(entity, "Empty Table Name For Class: " + entityClass.getName());
        String tableName = entity.value();
        tableMeta.setTableName(tableName);
        // 获取所有字段
        List<Field> allFields = ReflectUtils.getAllFields(entityClass);
        String pkColumnName = null;
        for (Field field : allFields) {
            String propertyName = field.getName();
            DbField dbField = field.getAnnotation(DbField.class);
            String columnName = dbField.value();
            tableMeta.setMapping(propertyName, columnName);
            if (dbField.isPk()) {
                pkColumnName = columnName;
            }
        }
        Assert.hasText(pkColumnName, "No Primary Key For Class: " + entityClass.getName());
        CACHE.dbMetas.put(entityClass, tableMeta);
    }

    /**
     * 通过实体类获取表名
     *
     * @param entityClass 实体类
     * @return 表名
     */
    public static String getTableName(Class<?> entityClass) {
        TableMeta tableMeta = CACHE.dbMetas.get(entityClass);
        return tableMeta.getTableName();
    }

    /**
     * 通过实体类获取主键名
     *
     * @param entityClass 实体类
     * @return 主键名
     */
    public static String getPkColumnName(Class<?> entityClass) {
        TableMeta tableMeta = CACHE.dbMetas.get(entityClass);
        return tableMeta.getPkColumnName();
    }

    /**
     * 通过实体类和字段名获取数据库列名
     *
     * @param entityClass  实体类
     * @param propertyName 实体类字段名
     * @return 列明
     */
    public static String getColumnName(Class<?> entityClass, String propertyName) {
        TableMeta tableMeta = CACHE.dbMetas.get(entityClass);
        return tableMeta.getPropertyColumnMap().get(propertyName);
    }

    /**
     * 通过实体类和数据库列名获取实体类字段名
     *
     * @param entityClass 实体类
     * @param columnName  数据库列名
     * @return 列明
     */
    public static String getPropertyName(Class<?> entityClass, String columnName) {
        TableMeta tableMeta = CACHE.dbMetas.get(entityClass);
        return tableMeta.getColumnPropertyMap().get(columnName);
    }

    /**
     * 获取所有列
     *
     * @param entityClass 实体类
     * @return 列明，多个用逗号隔开
     */
    public static String getAllColumnNames(Class<?> entityClass) {
        TableMeta tableMeta = CACHE.dbMetas.get(entityClass);
        return String.join(Constants.COMMA, tableMeta.getColumnPropertyMap().keySet());
    }

}

    