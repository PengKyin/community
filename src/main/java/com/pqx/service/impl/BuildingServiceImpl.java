package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.BuildingMapper;
import com.pqx.pojo.Building;
import com.pqx.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public List<Building> findAll() {
        List<Building> buildings = buildingMapper.selectAll();
        return buildings;
    }

    @Override
    public Page<Building> search(Map searchMap) {
        Example example = new Example(Building.class);
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
        Page<Building> buildings = (Page<Building>) buildingMapper.selectByExample(example);
        return buildings;
    }

    @Override
    public Boolean add(Building building) {
        int row = buildingMapper.insert(building);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Building findById(Integer id) {
        return buildingMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Building building) {
        int row = buildingMapper.updateByPrimaryKeySelective(building);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

//    @Override
//    public Boolean updateStatus(String status, Integer id) {
//        Building building = new Building();
//        building.setId(id);
//        community.setStatus(status);
//        int row = communityMapper.updateByPrimaryKeySelective(community);
//        if (row > 0){
//            return true;
//        }else {
//            return false;
//        }
//    }

    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            buildingMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    @Override
    public List<String> getBuildingName(String communityName) {
        return buildingMapper.getBuildingName(communityName);
    }

    @Override
    public Integer getBuildingId(String buildingName) {
        return buildingMapper.getBuildingId(buildingName);
    }
}
