package com.leyou.order.service;

import com.leyou.order.enums.PayState;
import com.leyou.order.pojo.Order;

import java.util.Map;

/**
 * @author Mr.JK
 * @create 2020-06-02  21:41
 */
public interface OrderService {
    Long createOrder(Order order);

    Order queryOrderById(Long id);

    String createPayUrl(Long orderId);

    void handleNotify(Map<String, String> result);

    PayState queryOrderState(Long orderId);
}
