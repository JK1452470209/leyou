package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @outhor Mr.JK
 * @create 2020-05-13  20:49
 * 分类实体类对应数据库表tb_tb_category
 */
@Data
@Table(name="tb_category")
public class Category {
    @Id
    @KeySql(useGeneratedKeys=true)
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    private Integer sort;

}
