package com.leyou.item.service;

import com.leyou.common.dto.CartDTO;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-05-17  15:33
 */
public interface GoodsService {
    PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key);

    void saveGoods(Spu spu);

    SpuDetail queryDetailById(Long spuId);

    List<Sku> querySkuBySpuId(Long spuId);

    void updateGoods(Spu spu);

    Spu querySpuById(Long id);

    List<Sku> querySkuByIds(List<Long> ids);

    void decreaseStock(List<CartDTO> carts);
}
