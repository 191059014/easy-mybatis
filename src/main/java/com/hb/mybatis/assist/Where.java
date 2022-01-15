package com.hb.mybatis.assist;

import com.hb.mybatis.enums.SqlKeyword;
import com.hb.mybatis.util.SqlScriptUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * where条件
 *
 * @version v0.1, 2022/1/13 22:24, create by huangbiao.
 */
public class Where implements Constants {

    /**
     * 条件语句
     */
    private StringBuilder conditionSql = new StringBuilder();

    /**
     * order by 语句
     */
    private String orderBySql = EMPTY;

    /**
     * group by 语句
     */
    private String groupBySql = EMPTY;

    /**
     * limit 语句
     */
    private String limitSql = EMPTY;

    /**
     * 参数集合
     */
    private Map<String, Object> params = new HashMap<>(16);

    /**
     * 构建查询条件对象
     *
     * @return Where
     */
    public static Where create() {
        Where where = new Where();
        where.conditionSql.append(SqlKeyword.WHERE.getSqlSegment());
        return where;
    }

    /**
     * and
     *
     * @return Where
     */
    public Where and() {
        this.conditionSql.append(SqlKeyword.AND.getSqlSegment());
        return this;
    }

    /**
     * or
     *
     * @return Where
     */
    public Where or() {
        this.conditionSql.append(SqlKeyword.OR.getSqlSegment());
        return this;
    }

    /**
     * 左括号
     *
     * @return Where
     */
    public Where leftBracket() {
        this.conditionSql.append(LEFT_BRACKET);
        return this;
    }

    /**
     * 左括号
     *
     * @return Where
     */
    public Where rightBracket() {
        this.conditionSql.append(RIGHT_BRACKET);
        return this;
    }

    /**
     * 等于
     *
     * @param columnName 列名
     * @param value      值
     * @return Where
     */
    public Where equal(String columnName, Object value) {
        String parameter = SqlScriptUtils.decorateParameter(columnName);
        String equalSql = String.format(SqlKeyword.EQUAL.getSqlSegment(), columnName, parameter);
        this.conditionSql.append(equalSql);
        this.params.put(columnName, value);
        return this;
    }

    /**
     * 不等于
     *
     * @param columnName 列名
     * @param value      值
     * @return Where
     */
    public Where notEqual(String columnName, Object value) {
        String parameter = SqlScriptUtils.decorateParameter(columnName);
        String notEqualSql = String.format(SqlKeyword.NOT_EQUAL.getSqlSegment(), columnName, parameter);
        this.conditionSql.append(notEqualSql);
        this.params.put(columnName, value);
        return this;
    }

    /**
     * 大于
     *
     * @param columnName 列名
     * @param value      值
     * @return Where
     */
    public Where maxThan(String columnName, Object value) {
        String parameter = SqlScriptUtils.decorateParameter(columnName);
        String maxThanSql = String.format(SqlKeyword.MAX_THAN.getSqlSegment(), columnName, parameter);
        this.conditionSql.append(maxThanSql);
        this.params.put(columnName, value);
        return this;
    }

    /**
     * 大于等于
     *
     * @param columnName 列名
     * @param value      值
     * @return Where
     */
    public Where maxEqualThan(String columnName, Object value) {
        String parameter = SqlScriptUtils.decorateParameter(columnName);
        String maxEqualThanSql = String.format(SqlKeyword.MAX_EQUAL_THAN.getSqlSegment(), columnName, parameter);
        this.conditionSql.append(maxEqualThanSql);
        this.params.put(columnName, value);
        return this;
    }

    /**
     * 小于
     *
     * @param columnName 列名
     * @param value      值
     * @return Where
     */
    public Where minThan(String columnName, Object value) {
        String parameter = SqlScriptUtils.decorateParameter(columnName);
        String minThanSql = String.format(SqlKeyword.MIN_THAN.getSqlSegment(), columnName, parameter);
        this.conditionSql.append(minThanSql);
        this.params.put(columnName, value);
        return this;
    }

    /**
     * 小于等于
     *
     * @param columnName 列名
     * @param value      值
     * @return Where
     */
    public Where minEqualThan(String columnName, Object value) {
        String parameter = SqlScriptUtils.decorateParameter(columnName);
        String minEqualThanSql = String.format(SqlKeyword.MIN_EQUAL_THAN.getSqlSegment(), columnName, parameter);
        this.conditionSql.append(minEqualThanSql);
        this.params.put(columnName, value);
        return this;
    }

    /**
     * 为空
     *
     * @param columnName 列名
     * @return Where
     */
    public Where isNull(String columnName) {
        String isNullSql = String.format(SqlKeyword.IS_NULL.getSqlSegment(), columnName);
        this.conditionSql.append(isNullSql);
        return this;
    }

    /**
     * 不为空
     *
     * @param columnName 列名
     * @return Where
     */
    public Where isNotNull(String columnName) {
        String isNotNullSql = String.format(SqlKeyword.IS_NOT_NULL.getSqlSegment(), columnName);
        this.conditionSql.append(isNotNullSql);
        return this;
    }

    /**
     * in条件
     *
     * @param columnName 列名
     * @param values     值集合
     * @return Where
     */
    public Where in(String columnName, Collection<?> values) {
        List<String> paramSqlList = new LinkedList<>();
        Iterator<?> iterator = values.iterator();
        int paramIndex = 1;
        while (iterator.hasNext()) {
            Object value = iterator.next();
            String paramName = String.join(UNDERLINE, columnName, String.valueOf(paramIndex));
            String parameter = SqlScriptUtils.decorateParameter(paramName);
            paramSqlList.add(parameter);
            this.params.put(paramName, value);
            paramIndex++;
        }
        String itemSql = String.join(COMMA, paramSqlList);
        String inSql = String.format(SqlKeyword.IN.getSqlSegment(), columnName, itemSql);
        this.conditionSql.append(inSql);
        return this;
    }

    /**
     * in条件
     *
     * @param columnName 列名
     * @param values     值集合
     * @return Where
     */
    public Where notIn(String columnName, Collection<?> values) {
        List<String> paramSqlList = new LinkedList<>();
        Iterator<?> iterator = values.iterator();
        int paramIndex = 1;
        while (iterator.hasNext()) {
            Object value = iterator.next();
            String param = String.join(UNDERLINE, columnName, String.valueOf(paramIndex));
            String parameter = SqlScriptUtils.decorateParameter(param);
            paramSqlList.add(parameter);
            this.params.put(parameter, value);
            paramIndex++;
        }
        String itemSql = String.join(COMMA, paramSqlList);
        String inSql = String.format(SqlKeyword.NOT_IN.getSqlSegment(), columnName, itemSql);
        this.conditionSql.append(inSql);
        return this;
    }

    /**
     * 模糊匹配
     *
     * @param columnName 列名
     * @param value      值
     * @return Where
     */
    public Where like(String columnName, Object value) {
        String parameter = SqlScriptUtils.decorateParameter(columnName);
        String likeSql = String.format(SqlKeyword.LIKE.getSqlSegment(), columnName, parameter);
        this.conditionSql.append(likeSql);
        this.params.put(columnName, value);
        return this;
    }


    /**
     * between and条件
     *
     * @param columnName 列名
     * @param start      开始值
     * @param end        结束值
     * @return Where
     */
    public Where between(String columnName, Object start, Object end) {
        String parameter1 = SqlScriptUtils.decorateParameter(String.join(UNDERLINE, columnName, "1"));
        String parameter2 = SqlScriptUtils.decorateParameter(String.join(UNDERLINE, columnName, "2"));
        String betweenAndSql = String.format(SqlKeyword.BETWEEN.getSqlSegment(), columnName, parameter1, parameter2);
        this.conditionSql.append(betweenAndSql);
        this.params.put(parameter1, start);
        this.params.put(parameter2, end);
        return this;
    }

    /**
     * 排序
     *
     * @param orderBySql 排序sql
     * @return Where
     */
    public Where orderBy(String orderBySql) {
        this.orderBySql = String.format(SqlKeyword.ORDER_BY.getSqlSegment(), orderBySql);
        return this;
    }

    /**
     * 分组
     *
     * @param groupBySql 分组sql
     * @return Where
     */
    public Where groupBy(String groupBySql) {
        this.groupBySql = String.format(SqlKeyword.GROUP_BY.getSqlSegment(), groupBySql);
        return this;
    }

    /**
     * 分页
     *
     * @param startRow 起始行
     * @param pageSize 每页条数
     * @return Where
     */
    public Where limit(int startRow, int pageSize) {
        this.limitSql = String.format(SqlKeyword.LIMIT.getSqlSegment(), startRow, pageSize);
        return this;
    }

    /**
     * 获取条件sql
     *
     * @return 条件sql
     */
    public String getConditionSql() {
        String conditionSql = this.conditionSql.toString();
        // 加上逻辑删除有效字段
        if (conditionSql.length() > SqlKeyword.WHERE.getSqlSegment().length()) {
            conditionSql += SqlKeyword.AND.getSqlSegment();
        }
        conditionSql += SqlScriptUtils.getLogicInvalidSql();
        return conditionSql;
    }

    /**
     * 获取where完整sql
     *
     * @return addSql
     */
    public String getWhereSql() {
        String fullSql = getConditionSql();
        // 加上排序sql
        if (!StringUtils.isEmpty(this.orderBySql)) {
            fullSql += this.orderBySql;
        }
        // 加上分组sql
        if (!StringUtils.isEmpty(this.groupBySql)) {
            fullSql += this.groupBySql;
        }
        // 加上分页sql
        if (!StringUtils.isEmpty(this.limitSql)) {
            fullSql += this.limitSql;
        }
        return fullSql;
    }

    /**
     * 获取where查询条件参数
     *
     * @return map集合
     */
    public Map<String, Object> getWhereParams() {
        return this.params;
    }

}
