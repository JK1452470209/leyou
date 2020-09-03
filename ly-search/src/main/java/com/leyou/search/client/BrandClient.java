package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Mr.JK
 * @create 2020-05-19  17:34
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
