package com.leyou.order.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Mr.JK
 * @create 2020-06-03  10:32
 */
@FeignClient("item-service")
public interface GoodClient extends GoodsApi {
}
