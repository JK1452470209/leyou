package com.leyou.common.vo;

import lombok.Data;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-05-14  13:39
 * 处理分页结果
 * view object
 */
@Data
public class PageResult<T> {
    private Long total;//总条数
    private Integer totalPage;//总页数
    private List<T> items;//当前页数据

    public PageResult(){

    }

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
