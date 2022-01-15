package com.hb.mybatis.method;

import com.hb.mybatis.assist.TableMetaCache;
import com.hb.mybatis.enums.SqlMethod;
import com.hb.mybatis.util.ReflectUtils;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 插入
 *
 * @version v0.1, 2022/1/13 22:37, create by huangbiao.
 */
public class Insert extends AbstractMethod {

    /**
     * 实体类
     */
    private Object entity = null;

    Map<String,Object> columnMap = new HashMap<>(16);

    public Insert(Object entity) {
        super(entity.getClass(), SqlMethod.INSERT);
        Assert.notNull(entity, "Entity Is Null");
        this.entity = entity;
    }

    @Override
    public String getSqlSegment() {
        Map<String, Object> propertyMap = ReflectUtils.getPropertyMap(entity);
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            String columnName = TableMetaCache.getColumnName(entityClass, key);
            columnMap.put(columnName,value);
        }
        return null;
    }
}

    