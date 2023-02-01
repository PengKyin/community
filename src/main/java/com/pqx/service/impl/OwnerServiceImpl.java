package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.OwnerMapper;
import com.pqx.pojo.Owner;
import com.pqx.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = ownerMapper.selectAll();
        return owners;
    }

    @Override
    public Page<Owner> search(Map searchMap) {
        Example example = new Example(Owner.class);
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();
            if (StringUtil.isNotEmpty((String) searchMap.get("startTime"))){
                criteria.andGreaterThanOrEqualTo("createTime",searchMap.get("startTime"));
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("endTime"))){
                criteria.andLessThanOrEqualTo("createTime",searchMap.get("endTime"));
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("name"))){
                criteria.andLike("name", "%"+(String) searchMap.get("name")+"%");
            }

//            if (StringUtil.isNotEmpty((String) searchMap.get("pageNum"))){
//                pageNum = Integer.parseInt((String) searchMap.get("pageNum"));
//            }
//            if (StringUtil.isNotEmpty((String) searchMap.get("pageSize"))){
//                pageSize = Integer.parseInt((String) searchMap.get("pageSize"));
//            }
            if (searchMap.get("pageNum") != null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if (searchMap.get("pageSize") != null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<Owner> owners = (Page<Owner>) ownerMapper.selectByExample(example);
        return owners;
    }

    @Override
    public Boolean add(Owner owner) {
        int row = ownerMapper.insert(owner);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Owner findById(Integer id) {
        return ownerMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Owner owner) {
        int row = ownerMapper.updateByPrimaryKeySelective(owner);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            ownerMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    @Override
    public List<Integer> getCommunity() {
        List<Integer> community = ownerMapper.getCommunity();
        return community;
    }

    @Override
    public List<Integer> getHouse(Integer communityId) {
        List<Integer> house = ownerMapper.getHouse(communityId);
        return house;
    }
}
