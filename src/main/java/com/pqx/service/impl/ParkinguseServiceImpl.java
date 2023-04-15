package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.ParkingMapper;
import com.pqx.dao.ParkinguseMapper;
import com.pqx.pojo.Community;
import com.pqx.pojo.ParkingUse;
import com.pqx.service.ParkinguseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ParkinguseServiceImpl implements ParkinguseService {

    @Autowired
    private ParkinguseMapper parkinguseMapper;
    @Override
    public List<ParkingUse> findAll() {
        List<ParkingUse> parkingUses = parkinguseMapper.selectAll();
        return parkingUses;
    }

    @Override
    public Page<ParkingUse> search(Map searchMap) {
        Example example = new Example(ParkingUse.class);
        int pageNum = 1;
        int pageSize = 2;
        if (searchMap != null){
            Example.Criteria criteria = example.createCriteria();
            if (StringUtil.isNotEmpty((String) searchMap.get("startTime"))){
                criteria.andGreaterThanOrEqualTo("startTime",searchMap.get("startTime"));
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("endTime"))){
                criteria.andLessThanOrEqualTo("startTime",searchMap.get("endTime"));
            }
            if (StringUtil.isNotEmpty((String) searchMap.get("carNumber"))){
                criteria.andLike("carNumber", "%"+(String) searchMap.get("carNumber")+"%");
            }
            if (searchMap.get("pageNum") != null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if (searchMap.get("pageSize") != null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<ParkingUse> parkingUses = (Page<ParkingUse>) parkinguseMapper.selectByExample(example);
        return parkingUses;
    }

    @Override
    public Boolean add(ParkingUse parkingUse) {
        int row = parkinguseMapper.insert(parkingUse);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ParkingUse findById(Integer id) {
        return parkinguseMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(ParkingUse parkingUse) {
        int row = parkinguseMapper.updateByPrimaryKeySelective(parkingUse);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        ParkingUse parkingUse = new ParkingUse();
        parkingUse.setId(id);
        int row = parkinguseMapper.updateByPrimaryKeySelective(parkingUse);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            parkinguseMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    @Override
    public List<String> getParking(String communityName) {
        List<String> parking = parkinguseMapper.getParking(communityName);
        return parking;
    }
}
