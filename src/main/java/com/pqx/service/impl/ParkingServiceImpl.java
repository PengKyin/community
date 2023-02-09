package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.ParkingMapper;
import com.pqx.pojo.Parking;
import com.pqx.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private ParkingMapper parkingMapper;
    @Override
    public List<Parking> findAll() {
        List<Parking> parkings = parkingMapper.selectAll();
        return parkings;
    }

    @Override
    public Page<Parking> search(Map searchMap) {
        Example example = new Example(Parking.class);
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
            if (StringUtil.isNotEmpty((String) searchMap.get("code"))){
                criteria.andLike("code", "%"+(String) searchMap.get("code")+"%");
            }
            if (searchMap.get("pageNum") != null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if (searchMap.get("pageSize") != null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<Parking> parkings = (Page<Parking>) parkingMapper.selectByExample(example);
        return parkings;
    }

    @Override
    public Boolean add(Parking parking) {
        int row = parkingMapper.insert(parking);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Parking findById(Integer id) {
        return parkingMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Parking parking) {
        int row = parkingMapper.updateByPrimaryKeySelective(parking);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Parking parking = new Parking();
        parking.setId(id);
        parking.setStatus(status);
        int row = parkingMapper.updateByPrimaryKeySelective(parking);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            parkingMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    @Override
    public List<String> getCommunityName() {

        return parkingMapper.getCommunityName();
    }

    @Override
    public Integer getCommunityId(String communityName) {
       return parkingMapper.getCommunityId(communityName);
    }
}
