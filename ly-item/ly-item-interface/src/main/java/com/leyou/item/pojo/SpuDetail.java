package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @outhor Mr.JK
 * @create 2020-05-17  15:31
 */
@Table(name="tb_spu_detail")
@Data
public class SpuDetail {
    @Id
    @Column(name = "`spu_id`")
    private Long spuId;// 对应的SPU的id
    private String description;// 商品描述

    @Column(name = "`special_spec`")
    private String specialSpec;// 商品特殊规格的名称及可选值模板
    @Column(name = "`generic_spec`")
    private String genericSpec;// 商品的全局规格属性
    @Column(name = "`packing_list`")
    private String packingList;// 包装清单
    @Column(name = "`after_service`")
    private String afterService;// 售后服务
}