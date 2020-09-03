package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @outhor Mr.JK
 * @create 2020-05-14  13:26
 * 品牌实体类对应数据库表tb_brand
 */
@Data
@Table(name = "tb_brand")
public class Brand {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;//品牌名称
    private String image;//品牌图片
    private Character letter;

}
