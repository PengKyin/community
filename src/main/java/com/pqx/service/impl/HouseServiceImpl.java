package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.HouseMapper;
import com.pqx.pojo.House;
import com.pqx.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public List<House> findAll() {
        List<House> houses = houseMapper.selectAll();
        return houses;
    }

    @Override
    public Page<House> search(Map searchMap) {
        Example example = new Example(House.class);
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();
            if (StringUtil.isNotEmpty((String) searchMap.get("startTime"))){
                criteria.andGreaterThanOrEqualTo("liveTime",searchMap.get("startTime"));
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("endTime"))){
                criteria.andLessThanOrEqualTo("liveTime",searchMap.get("endTime"));
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
        Page<House> houses = (Page<House>) houseMapper.selectByExample(example);
        return houses;
    }

    @Override
    public Boolean add(House house) {
        int row = houseMapper.insert(house);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public House findById(Integer id) {
        return houseMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(House house) {
        int row = houseMapper.updateByPrimaryKeySelective(house);
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
            houseMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    @Override
    public List<String> getOptions() {
        List<String> option = houseMapper.getOption();
        return option;
    }

    @Override
    public List<String> getBuildingName(House house) {
        List<String> buildingName = houseMapper.getBuildingName(house.getCommunityName());
        return buildingName;
    }
}
