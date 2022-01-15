package com.hb.mybatis.assist;

/**
 * 常量类
 *
 * @version v0.1, 2022/1/13 22:24, create by huangbiao.
 */
public interface Constants {

    /**
     * 空字符串
     */
    String EMPTY = "";

    /**
     * 获取参数的统一格式
     */
    String PROPERTY_FORMAT = "#{params.%s}";

    /**
     * 左括号
     */
    String LEFT_BRACKET = "(";

    /**
     * 左括号
     */
    String RIGHT_BRACKET = ")";

    /**
     * 下划线
     */
    String UNDERLINE = "_";

    /**
     * 逗号
     */
    String COMMA = ",";

    /**
     * 星号
     */
    String STAR = "*";

    /**
     * 等号
     */
    String EQUAL = "=";

    /**
     * 逻辑状态字段名
     */
    String LOGIC_STATUS_PROPERTY = "isValid";

    /**
     * 逻辑状态列名
     */
    String LOGIC_STATUS_COLUMN = "is_valid";

    /**
     * 逻辑有效
     */
    int LOGIC_VALID = 1;

    /**
     * 逻辑无效
     */
    int LOGIC_INVALID = 0;

}
