package com.leyou.page.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author Mr.JK
 * @create 2020-05-19  17:34
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
