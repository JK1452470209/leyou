package com.leyou.item.service;

import com.leyou.item.pojo.Category;

import java.util.List;

/**
 * @outhor Mr.JK
 * @create 2020-05-14  13:31
 */
public interface CategoryService {
    /**
     * 根据id查询所有类别
     * @param pid
     * @return
     */
    List<Category>  queryCategoryListByPid(Long pid);

    /**
     * 新增保存类别
     * @param category
     */
    void saveCategory(Category category);

    /**
     * 删除类别
     * @param id
     */
    void deleteCategory(Long id);

    /**
     * 更新类别
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 根据id查询所有品牌
     * @param bid
     * @return
     */
    List<Category> queryByBrandId(Long bid);

    /**
     * 根据集合id查询品牌
     * @param ids
     * @return
     */
    List<Category> queryByIds(List<Long> ids);

    /**
     * 根据Cid3查询全部分类
     * @param id
     * @return
     */
    List<Category> queryAllByCid3(Long id);
}
