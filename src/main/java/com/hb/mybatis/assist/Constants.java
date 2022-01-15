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
     * 等号
     */
    String EQUAL = "=";

}
