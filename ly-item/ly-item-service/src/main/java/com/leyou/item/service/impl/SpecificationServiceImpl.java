package com.leyou.item.service.impl;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @outhor Mr.JK
 * @create 2020-05-17  13:54
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    /**
     * 规格组
     */
    @Autowired
    private SpecGroupMapper groupMapper;

    /**
     * 参数组
     */
    @Autowired
    private SpecParamMapper paramMapper;

    @Override
    public List<SpecGroup> queryGroupByCid(Long cid) {

        //查询条件
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        //查询
        List<SpecGroup> list = groupMapper.select(group);
        if (CollectionUtils.isEmpty(list)){
            //没有查到
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return list;
    }

    @Override
    public List<SpecParam> queryParamList(Long gid,Long cid,Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = paramMapper.select(param);
        if (CollectionUtils.isEmpty(list)){
            //没有查到
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
        }
        return list;

    }

    @Override
    public void saveGroup(SpecGroup specGroup) {
        groupMapper.insert(specGroup);
    }

    @Override
    public void saveParam(SpecParam specParam) {
        specParam.setId(null);
        paramMapper.insert(specParam);
    }

    @Override
    public void updateGroup(SpecGroup specGroup) {
        groupMapper.updateByPrimaryKey(specGroup);
    }

    @Override
    public void updateParam(SpecParam specparam) {
        paramMapper.updateByPrimaryKey(specparam);
    }

    @Override
    public void deleteGroup(Long id) {
        SpecGroup group = new SpecGroup();
        group.setId(id);
        groupMapper.delete(group);
        //组删后 组后的参数一并删除
        SpecParam param = new SpecParam();
        param.setGroupId(id);
        List<SpecParam> params = paramMapper.select(param);
        for (SpecParam specParam : params) {
            paramMapper.delete(specParam);
        }
    }

    @Override
    public List<SpecGroup> queryListByCid(Long cid) {
        //查询规格组
        List<SpecGroup> specGroups = queryGroupByCid(cid);
        //查询当前分类下的参数
        List<SpecParam> specParams = queryParamList(null, cid, null);
        //先把规格参数变为map，map的key是规格组id，map的值是组下的所有参数
        Map<Long,List<SpecParam>> map = new HashMap<>();
        for (SpecParam param : specParams) {
            if (!map.containsKey(param.getGroupId())){
                //这个组id在map中不存在，新增一个list
                map.put(param.getGroupId(),new ArrayList<>());
            }
            map.get(param.getGroupId()).add(param);
        }
        //填充param到group
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));

        }

        return specGroups;
    }

    @Override
    public void deleteParam(Long id) {
        SpecParam param = new SpecParam();
        param.setId(id);
        paramMapper.delete(param);
    }




}
