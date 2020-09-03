package com.leyou.search.service;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;

/**
 * @author Mr.JK
 * @create 2020-06-01  20:11
 */
public interface SearchService {

    /**
     * 根据spu查询搜索过滤后的商品
     * @param spu
     * @return
     */
    Goods buildGoods(Spu spu);

    /**
     * 根据搜索条件返回分页后的商品
     * @param request
     * @return
     */
    PageResult<Goods> search(SearchRequest request);

    /**
     * 根据spuid更新索引库商品
     * @param spuId
     */
    void createOrUpdateIndex(Long spuId);

    /**
     * 根据spuid删除索引库商品
     * @param spuId
     */
    void deleteIndex(Long spuId);

}
