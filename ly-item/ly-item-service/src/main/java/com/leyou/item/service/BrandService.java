package com.leyou.item.service;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-05-14  13:30
 */
public interface BrandService {

    /**
     * 查询出分页结果并封装返回
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key);

    /**
     * 新增保存品牌
     * @param brand
     * @param cids
     */
    void saveBrand(Brand brand, List<Long> cids);

    /**
     * 根据id查询品牌
     * @param id
     * @return
     */
    Brand queryById(Long id);

    /**
     * 更新品牌
     * @param brand
     */
    void updateBrand(Brand brand);

    /**
     * 根据id删除品牌
     * @param id
     */
    void deleteBrand(Long id);

    /**
     * 根据cid查询品牌
     * @param cid
     * @return
     */
    List<Brand> queryBrandByCid(Long cid);

    List<Brand> querybyIds(List<Long> ids);
}
