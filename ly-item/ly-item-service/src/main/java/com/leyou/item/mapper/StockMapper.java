package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author Mr.JK
 * @create 2020-05-18  13:55
 */
public interface StockMapper extends BaseMapper<Stock> {

    @Update("update tb_stock set stock = stock - #{num} where sku_id = #{id} and stock >= #{num}")
    int decreaseStock(@Param("id") Long id,@Param("num") Integer num);


}
