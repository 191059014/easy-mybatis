package com.hb.mybatis.dao;

import com.hb.mybatis.assist.Where;
import com.hb.mybatis.util.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * mapper超级接口
 *
 * @version v0.1, 2022/1/13 22:22, create by huangbiao.
 */
public interface IBaseMapper<T> {

    /**
     * 插入
     *
     * @param entity 实体类对象
     * @return 插入行数
     */
    int insert(Object entity);

    /**
     * 通过id删除
     *
     * @param id 主键
     * @return 影响的行数
     */
    int deleteById(Object id);

    /**
     * 通过id集合删除
     *
     * @param ids id集合
     * @return 影响的行数
     */
    int deleteBatchIds(Collection<?> ids);

    /**
     * 条件删除
     *
     * @param where where条件对象
     * @return 影响的行数
     */
    int deleteByCondition(Where where);

    /**
     * 通过id逻辑删除
     *
     * @param id 主键
     * @return 影响的行数
     */
    int logicDeleteById(Object id);

    /**
     * 通过id集合逻辑删除
     *
     * @param ids id集合
     * @return 影响的行数
     */
    int logicDeleteBatchIds(Collection<?> ids);

    /**
     * 根据条件逻辑删除
     *
     * @param where where条件对象
     * @return 影响的行数
     */
    int logicDeleteByCondition(Where where);

    /**
     * 通过id更新
     *
     * @param entity 更新的信息
     * @return 影响的行数
     */
    int updateById(Object entity);

    /**
     * 条件更新
     *
     * @param propertyMap 属性值集合
     * @param where       条件
     * @return 影响的行数
     */
    int updateByCondition(Map<String, Object> propertyMap, Where where);

    /**
     * 根据id查询
     *
     * @param id 主键
     * @return 结果
     */
    T selectById(Object id);

    /**
     * 根据id集合查询
     *
     * @param ids id集合
     * @return 结果
     */
    List<T> selectBatchIds(Collection<?> ids);

    /**
     * 查询单条数据
     *
     * @param where where条件
     * @return 结果
     */
    T selectOne(Where where);

    /**
     * 查询多条数据
     *
     * @param where where条件
     * @return 结果
     */
    List<T> selectList(Where where);

    /**
     * 查询多条数据
     *
     * @param resultSql 结果sql
     * @param where     where条件
     * @return 数据集合
     */
    List<T> selectList(String resultSql, Where where);

    /**
     * 查询总条数
     *
     * @param where where条件
     * @return 总条数
     */
    Long selectCount(Where where);

    /**
     * 分页查询
     *
     * @param where where条件
     * @return 分页集合
     */
    Page<T> selectPages(Where where);

    /**
     * 分页查询
     *
     * @param resultSql 结果sql
     * @param where     where条件
     * @return 分页集合
     */
    Page<T> selectPages(String resultSql, Where where);

}
