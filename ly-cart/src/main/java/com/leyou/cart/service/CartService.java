package com.leyou.cart.service;

import com.leyou.cart.pojo.Cart;

import java.util.List;

/**
 * @author Mr.JK
 * @create 2020-05-29  10:18
 */
public interface CartService {
    void addCart(Cart cart);

    List<Cart> queryCartList();

    void updateNum(Long skuId, Integer num);

    void deleteCart(Long skuId);
}
