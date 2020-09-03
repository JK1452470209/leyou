package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import com.leyou.item.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Mr.JK
 * @create 2020-05-19  17:11
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
