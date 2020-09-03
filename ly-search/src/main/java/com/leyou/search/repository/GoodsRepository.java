package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Mr.JK
 * @create 2020-05-19  17:37
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
