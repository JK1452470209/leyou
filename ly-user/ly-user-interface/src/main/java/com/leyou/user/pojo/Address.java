package com.leyou.user.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mr.JK
 * @create 2020-06-06  12:00
 */
@Data
@Table(name = "tb_address")
public class Address {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "zipCode")
    private String zipCode;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "address")
    private String address;

    @Column(name = "label")
    private String label;

    @Column(name = "defaultAddress")
    private Boolean defaultAddress;

}
