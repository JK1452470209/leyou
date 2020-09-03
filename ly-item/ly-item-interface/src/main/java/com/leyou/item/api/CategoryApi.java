package com.leyou.item.api;

import com.leyou.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Mr.JK
 * @create 2020-05-19  17:30
 */
public interface CategoryApi {
    @GetMapping("category/list/ids")
    List<Category> queryGategoryByIds(@RequestParam("ids") List<Long> ids);
}
