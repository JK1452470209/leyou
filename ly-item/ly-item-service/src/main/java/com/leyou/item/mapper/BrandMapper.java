package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-05-14  13:29
 */
public interface BrandMapper extends BaseMapper<Brand> {
    /**
     * 根据分类id和品牌id保存中间表
     * @param id
     * @param bid
     * @return
     */
    @Insert("INSERT INTO tb_category_brand(category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long id,@Param("bid") Long bid);

    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> queryByCategoryCid(@Param("cid") Long cid);




}
