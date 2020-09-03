package com.leyou.page.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Mr.JK
 * @create 2020-05-19  17:11
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
