package com.hb.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * mapper基类
 *
 * @version v0.1, 2022/1/13 14:52, create by huangbiao.
 */
public interface DmlMapper {

    /**
     * 选择性插入
     *
     * @param sqlStatement sql语句
     * @param params       条件集合
     * @return 插入的条数
     */
    int insertBySelective(@Param("sqlStatement") String sqlStatement, @Param("params") Map<String, Object> params);

    /**
     * 选择性删除
     *
     * @param sqlStatement sql语句
     * @param params       条件集合
     * @return 更新的条数
     */
    int deleteBySelective(@Param("sqlStatement") String sqlStatement, @Param("params") Map<String, Object> params);

    /**
     * 选择性更新
     *
     * @param sqlStatement sql语句
     * @param params       条件集合
     * @return 更新的条数
     */
    int updateBySelective(@Param("sqlStatement") String sqlStatement, @Param("params") Map<String, Object> params);


    /**
     * 动态条件查询集合
     *
     * @param sqlStatement sql语句
     * @param params       条件集合
     * @return 结果集合
     */
    List<Map<String, Object>> selectList(@Param("sqlStatement") String sqlStatement, @Param("params") Map<String, Object> params);

    /**
     * 查询总条数
     *
     * @param sqlStatement sql语句
     * @param params       条件集合
     * @return 结果集合
     */
    Long selectCount(@Param("sqlStatement") String sqlStatement, @Param("params") Map<String, Object> params);

}
