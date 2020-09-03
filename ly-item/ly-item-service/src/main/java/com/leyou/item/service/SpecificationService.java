package com.leyou.item.service;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;

import java.util.List;

/**
 * @author Mr.JK
 * @create 2020-05-17  13:53
 */
public interface SpecificationService {
    /**
     * 根据cid查询规格组
     * @param cid
     * @return
     */
    List<SpecGroup> queryGroupByCid(Long cid);

    /**
     * 根据gid查询参数
     * @param gid
     * @return
     */
    List<SpecParam> queryParamList(Long gid,Long cid,Boolean searching);

    /**
     * 新增保存参数
     * @param specParam
     */
    void saveParam(SpecParam specParam);

    /**
     * 新增保存规格组
     * @param specGroup
     */
    void saveGroup(SpecGroup specGroup);

    /**
     * 更新规格组
     * @param specGroup
     */
    void updateGroup(SpecGroup specGroup);

    /**
     * 更新参数
     * @param specparam
     */
    void updateParam(SpecParam specparam);

    /**
     * 删除参数
     * @param id
     */
    void deleteParam(Long id);

    /**
     * 删除规格组
     * @param id
     */
    void deleteGroup(Long id);

    /**
     * 根据cid查询规格组与规格参数
     * @param cid
     * @return
     */
    List<SpecGroup> queryListByCid(Long cid);
}
