package com.hb.mybatis.dao.impl;

import com.hb.mybatis.assist.Constants;
import com.hb.mybatis.assist.TableMetaCache;
import com.hb.mybatis.assist.Where;
import com.hb.mybatis.dao.IBaseMapper;
import com.hb.mybatis.enums.SqlMethod;
import com.hb.mybatis.mapper.DmlMapper;
import com.hb.mybatis.util.Page;
import com.hb.mybatis.util.ReflectUtils;
import com.hb.mybatis.util.SqlScriptUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * mapper超级接口
 *
 * @version v0.1, 2022/1/13 22:22, create by huangbiao.
 */
public class BaseMapperImpl<T> implements IBaseMapper<T>, InitializingBean {

    /**
     * mapper基类
     */
    @Resource
    private DmlMapper dmlMapper;

    /**
     * 实体类
     */
    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        // 获取泛型class
        entityClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        // 表结构放入缓存统一管理
        TableMetaCache.put(entityClass);
    }

    /**
     * 插入
     *
     * @param entity 实体类对象
     * @return 插入行数
     */
    @Override
    public int insert(Object entity) {
        Assert.notNull(entity, "Entity Is Null");
        Map<String, Object> propertyMap = ReflectUtils.getPropertyMap(entity);
        List<String> columnList = new LinkedList<>();
        List<String> propertyList = new LinkedList<>();
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            String columnName = TableMetaCache.getColumnName(entityClass, key);
            columnList.add(columnName);
            propertyList.add(SqlScriptUtils.decorateParameter(key));
        }
        Assert.notEmpty(columnList, "No Column To Insert");
        Assert.notEmpty(propertyList, "No Value To Insert");
        String columnSql = String.join(Constants.COMMA, columnList);
        String paramSql = String.join(Constants.COMMA, propertyList);
        String tableName = TableMetaCache.getTableName(entityClass);
        String sqlStatement = String.format(SqlMethod.INSERT.getSqlSegment(), tableName, columnSql, paramSql);
        return dmlMapper.insertBySelective(sqlStatement, propertyMap);
    }

    /**
     * 通过主键删除
     *
     * @param id 主键
     * @return 影响的行数
     */
    @Override
    public int deleteById(Object id) {
        Assert.notNull(id, "Id Is Null");
        String pkColumnName = TableMetaCache.getPkColumnName(entityClass);
        return deleteByCondition(Where.create().equal(pkColumnName, id));
    }

    /**
     * 通过id集合删除
     *
     * @param ids id集合
     * @return 影响的行数
     */
    @Override
    public int deleteBatchIds(Collection<?> ids) {
        Assert.notEmpty(ids, "Id Collection Is Null");
        String pkColumnName = TableMetaCache.getPkColumnName(entityClass);
        return deleteByCondition(Where.create().in(pkColumnName, ids));
    }

    /**
     * 条件删除
     *
     * @param where where条件对象
     * @return 影响的行数
     */
    @Override
    public int deleteByCondition(Where where) {
        Assert.notNull(where, "Condition Is Null");
        String tableName = TableMetaCache.getTableName(entityClass);
        String methodSql = String.format(SqlMethod.DELETE.getSqlSegment(), tableName);
        String whereSql = where.getWhereSql();
        Map<String, Object> whereParams = where.getWhereParams();
        return dmlMapper.deleteBySelective(methodSql + whereSql, whereParams);
    }

    /**
     * 通过id逻辑删除
     *
     * @param id 主键
     * @return 影响的行数
     */
    @Override
    public int logicDeleteById(Object id) {
        String pkColumnName = TableMetaCache.getPkColumnName(entityClass);
        return logicDeleteByCondition(Where.create().equal(pkColumnName, id));
    }

    /**
     * 通过id集合逻辑删除
     *
     * @param ids id集合
     * @return 影响的行数
     */
    @Override
    public int logicDeleteBatchIds(Collection<?> ids) {
        String pkColumnName = TableMetaCache.getPkColumnName(entityClass);
        return logicDeleteByCondition(Where.create().in(pkColumnName, ids));
    }

    /**
     * 根据条件逻辑删除
     *
     * @param where where条件对象
     * @return 影响的行数
     */
    @Override
    public int logicDeleteByCondition(Where where) {
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put(Constants.LOGIC_STATUS_PROPERTY, Constants.LOGIC_INVALID);
        return updateByCondition(propertyMap, where);
    }

    /**
     * 通过ID更新
     *
     * @param entity 更新的信息
     * @return 影响的行数
     */
    @Override
    public int updateById(Object entity) {
        Assert.notNull(entity, "Entity Is Null");
        String pkColumnName = TableMetaCache.getPkColumnName(entityClass);
        Map<String, Object> propertyMap = ReflectUtils.getPropertyMap(entity);
        String propertyName = TableMetaCache.getPropertyName(entityClass, pkColumnName);
        Object idValue = propertyMap.get(propertyName);
        propertyMap.remove(pkColumnName);
        return updateByCondition(propertyMap, Where.create().equal(pkColumnName, idValue));
    }

    /**
     * 条件更新
     *
     * @param propertyMap 属性值集合
     * @param where       条件
     * @return 影响的行数
     */
    @Override
    public int updateByCondition(Map<String, Object> propertyMap, Where where) {
        Assert.notEmpty(propertyMap, "No Value To Update");
        Assert.notNull(where, "Condition Is Null");
        List<String> updateKeys = new ArrayList<>();
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> whereParams = where.getWhereParams();
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            String columnName = TableMetaCache.getColumnName(entityClass, key);
            String paramName = columnName;
            if (whereParams.containsKey(paramName)) {
                // 当需要更新的值和where条件相同的时候，改变名称
                paramName = paramName + Constants.UNDERLINE;
            }
            updateKeys.add(columnName + Constants.EQUAL + SqlScriptUtils.decorateParameter(paramName));
            paramMap.put(paramName, value);
        }
        Assert.notEmpty(updateKeys, "No Value To Update");
        String updateKeysSql = String.join(Constants.COMMA, updateKeys);
        String tableName = TableMetaCache.getTableName(entityClass);
        String methodSql = String.format(SqlMethod.UPDATE.getSqlSegment(), tableName, updateKeysSql);
        String whereSql = where.getWhereSql();
        paramMap.putAll(whereParams);
        return dmlMapper.updateBySelective(methodSql + whereSql, paramMap);
    }

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return 结果
     */
    @Override
    public T selectById(Object id) {
        String pkColumnName = TableMetaCache.getPkColumnName(entityClass);
        return selectOne(Where.create().equal(pkColumnName, id));
    }

    /**
     * 根据id集合查询
     *
     * @param ids id集合
     * @return 结果
     */
    @Override
    public List<T> selectBatchIds(Collection<?> ids) {
        String pkColumnName = TableMetaCache.getPkColumnName(entityClass);
        return selectList(Where.create().in(pkColumnName, ids));
    }

    /**
     * 查询单条数据
     *
     * @param where where条件
     * @return 结果
     */
    @Override
    public T selectOne(Where where) {
        List<T> list = selectList(where);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 查询多条数据
     *
     * @param where where条件
     * @return 结果
     */
    @Override
    public List<T> selectList(Where where) {
        return selectList(Constants.STAR, where);
    }

    /**
     * 查询多条数据
     *
     * @param resultSql 结果sql
     * @param where     where条件
     * @return 数据集合
     */
    @Override
    public List<T> selectList(String resultSql, Where where) {
        Assert.hasText(resultSql, "Result Sql Is Empty");
        Assert.notNull(where, "Condition Is Null");
        String tableName = TableMetaCache.getTableName(entityClass);
        String methodSql = String.format(SqlMethod.SELECT_LIST.getSqlSegment(), resultSql, tableName);
        String whereSql = where.getWhereSql();
        Map<String, Object> whereParams = where.getWhereParams();
        List<Map<String, Object>> columnList = dmlMapper.selectList(methodSql + whereSql, whereParams);
        List<Map<String, Object>> propertyMapList = new ArrayList<>();
        Map<String, Object> propertyMap = null;
        for (Map<String, Object> columnMap : columnList) {
            propertyMap = new HashMap<>();
            for (Map.Entry<String, Object> entry : columnMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                String propertyName = TableMetaCache.getPropertyName(entityClass, key);
                propertyMap.put(propertyName, value);
            }
            propertyMapList.add(propertyMap);
        }
        return ReflectUtils.mapList2BeanList(propertyMapList, entityClass);
    }

    /**
     * 查询总条数
     *
     * @param where where条件
     * @return 总条数
     */
    @Override
    public Long selectCount(Where where) {
        Assert.notNull(where, "Condition Is Null");
        String tableName = TableMetaCache.getTableName(entityClass);
        String methodSql = String.format(SqlMethod.SELECT_COUNT.getSqlSegment(), tableName);
        String whereSql = where.getConditionSql();
        Map<String, Object> whereParams = where.getWhereParams();
        return dmlMapper.selectCount(methodSql + whereSql, whereParams);
    }

    /**
     * 分页查询
     *
     * @param where where条件
     * @return 分页集合
     */
    @Override
    public Page<T> selectPages(Where where) {
        return selectPages(Constants.STAR, where);
    }

    /**
     * 分页查询
     *
     * @param resultSql 结果sql
     * @param where     where条件
     * @return 分页集合
     */
    @Override
    public Page<T> selectPages(String resultSql, Where where) {
        Long count = selectCount(where);
        List<T> list = selectList(resultSql, where);
        return new Page<>(list, count);
    }

}
