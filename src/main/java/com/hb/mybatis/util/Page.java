package com.hb.mybatis.util;

import java.util.List;

/**
 * 分页结果
 *
 * @version v0.1, 2022/1/15 10:25, create by huangbiao.
 */
public class Page<T> {

    /**
     * 数据集合
     */
    private List<T> data;

    /**
     * 总条数
     */
    private long total;

    public Page(List<T> data, long total) {
        this.data = data;
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public long getTotal() {
        return total;
    }

}

    