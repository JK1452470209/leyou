package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Mr.JK
 * @create 2020-05-19  17:20
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
