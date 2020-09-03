package com.leyou.cart.service.impl;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.interceptor.UserInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mr.JK
 * @create 2020-05-29  10:18
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "cart:uid:";

    @Override
    public void addCart(Cart cart) {
        //获取登录用户
        UserInfo user = UserInterceptor.getUser();

        //key
        String key = KEY_PREFIX + user.getId();

        //hashkey
        String hashkey = cart.getSkuId().toString();

        //记录num
        Integer num = cart.getNum();

        //获取登录用户的所有购物车
        BoundHashOperations<String, Object, Object> operation = redisTemplate.boundHashOps(key);

        //判断当前购物车商品是否存在
        if (operation.hasKey(hashkey)){
            //存在，修改数量
            String json = operation.get(hashkey).toString();
            cart = JsonUtils.parse(json, Cart.class);
            cart.setNum(cart.getNum() + num);

        }
        //写回redis
        operation.put(hashkey,JsonUtils.toString(cart));

    }

    @Override
    public List<Cart> queryCartList() {
        //获取登录用户
        UserInfo user = UserInterceptor.getUser();

        //key
        String key = KEY_PREFIX + user.getId();

        if (!redisTemplate.hasKey(key)){
            //key不存在，返回404
            throw new LyException(ExceptionEnum.CART_NOT_FOUND);
        }

        //获取登录用户的所有购物车
        BoundHashOperations<String, Object, Object> operation = redisTemplate.boundHashOps(key);

        List<Cart> cart = operation.values().stream()
                .map(o -> JsonUtils.parse(o.toString(), Cart.class))
                .collect(Collectors.toList());


        return cart;
    }

    @Override
    public void updateNum(Long skuId, Integer num) {
        //获取登录用户
        UserInfo user = UserInterceptor.getUser();
        //Key
        String key = KEY_PREFIX + user.getId();
        //hashkey
        String hashKey = skuId.toString();
        //获取操作
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(key);

        //判断是否存在
        if (!operations.hasKey(skuId.toString())) {
            throw new LyException(ExceptionEnum.CART_NOT_FOUND);
        }
        //查询购物车
        Cart cart = JsonUtils.parse(operations.get(skuId.toString()).toString(), Cart.class);
        cart.setNum(num);

        //写回redis
        operations.put(hashKey,JsonUtils.toString(cart));

    }

    @Override
    public void deleteCart(Long skuId) {
        //获取登录用户
        UserInfo user = UserInterceptor.getUser();
        //Key
        String key = KEY_PREFIX + user.getId();

        //删除
        redisTemplate.opsForHash().delete(key,skuId.toString());
    }
}
