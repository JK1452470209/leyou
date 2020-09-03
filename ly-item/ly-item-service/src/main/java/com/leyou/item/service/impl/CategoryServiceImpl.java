package com.leyou.item.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @outhor Mr.JK
 * @create 2020-05-13  20:55
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询本节点下所包含的所有叶子节点，用于维护tb_category_brand中间表
     * @param category
     * @param leafNode
     */
    private void queryAllleafNode(Category category,List<Category> leafNode){
        if (!category.getIsParent()){
            leafNode.add(category);
        }
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list = categoryMapper.selectByExample(example);

        for (Category category1:list){
            queryAllleafNode(category1,leafNode);
        }
    }

    /**
     * 查询本节点下的所有子节点
     * @param category
     * @param node
     */
    private void queryAllNode(Category category,List<Category> node){

        node.add(category);
        Example example = new Example(Category.class);
        example.createCriteria().andEqualTo("parentId",category.getId());
        List<Category> list = categoryMapper.selectByExample(example);

        for (Category category1:list){
            queryAllleafNode(category1,node);
        }

    }

    public List<Category> queryCategoryListByPid(Long pid) {
        //查询条件，mapper会把对象中的非空属性作为查询条件
        Category t = new Category();
        t.setParentId(pid);
        List<Category> list = categoryMapper.select(t);
        if (CollectionUtils.isEmpty(list)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return list;
    }

    @Override
    public void saveCategory(Category category) {
        /*
        * 将节点插入到数据库中
        * 将新增的类别父节点isParent设为true
        * 如果id不设置为null，默认id为0 数据库则不会递增
        * */
        //设置id为null
        category.setId(null);
        //保存
        categoryMapper.insert(category);
        //修改父节点，并根据主键更新
        Category parent = new Category();
        parent.setId(category.getParentId());
        parent.setIsParent(true);
        categoryMapper.updateByPrimaryKeySelective(parent);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category.getIsParent()){
            //1.查找所有叶子节点
            ArrayList<Category> list  = new ArrayList<>();
            queryAllleafNode(category,list);

            //2.查找所有子节点
            ArrayList<Category> list2  = new ArrayList<>();
            queryAllNode(category,list2);

            //3.删除tb_category中的数据，使用list2
            for (Category c : list2){
                categoryMapper.delete(c);
            }

            //4.维护中间表
            for (Category c : list){
                categoryMapper.deleteByCategoryIdInCategoryBrand(c.getId());
            }

        }else {
            //1.查询此节点的父亲节点的孩子个数 ==》 查询还有几个兄弟
            Example example = new Example(Category.class);
            example.createCriteria().andEqualTo("parentId",category.getParentId());
            List<Category> list = categoryMapper.selectByExample(example);
            if (list.size() != 1){
                //有兄弟，直接删除自己
                categoryMapper.deleteByPrimaryKey(category.getId());

                //维护中间表
                categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());
            }else {
                //已经没有兄弟了
                categoryMapper.deleteByPrimaryKey(category.getId());

                Category parent = new Category();
                parent.setId(category.getParentId());
                parent.setIsParent(false);
                categoryMapper.updateByPrimaryKeySelective(parent);

                //维护中间表
                categoryMapper.deleteByCategoryIdInCategoryBrand(category.getId());

            }
        }

    }

    @Override
    public void updateCategory(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public List<Category> queryByBrandId(Long bid) {
        return this.categoryMapper.queryByBrandId(bid);
    }

    @Override
    public List<Category> queryByIds(List<Long> ids) {
        List<Category> categories = categoryMapper.selectByIdList(ids);

        if (CollectionUtils.isEmpty(categories)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOND);
        }
        return categories;
    }

    @Override
    public List<Category> queryAllByCid3(Long id) {
        Category c3 = this.categoryMapper.selectByPrimaryKey(id);
        Category c2 = this.categoryMapper.selectByPrimaryKey(c3.getParentId());
        Category c1 = this.categoryMapper.selectByPrimaryKey(c2.getParentId());
        return Arrays.asList(c1,c2,c3);
    }


}
