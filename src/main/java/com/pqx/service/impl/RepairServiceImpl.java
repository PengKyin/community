package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.RepairMapper;
import com.pqx.pojo.Community;
import com.pqx.pojo.Repair;
import com.pqx.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class RepairServiceImpl implements RepairService {
    @Autowired
    private RepairMapper repairMapper;

    @Override
    public List<Repair> findAll() {
        List<Repair> repairs = repairMapper.selectAll();
        return repairs;
    }

    @Override
    public Page<Repair> search(Map searchMap) {
        Example example = new Example(Repair.class);
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
            if (StringUtil.isNotEmpty((String) searchMap.get("deviceName"))){
                criteria.andLike("deviceName", "%"+(String) searchMap.get("deviceName")+"%");
            }
            if (searchMap.get("pageNum") != null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if (searchMap.get("pageSize") != null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<Repair> repairs = (Page<Repair>) repairMapper.selectByExample(example);
        return repairs;
    }

    @Override
    public Boolean add(Repair repair) {
        int row = repairMapper.insert(repair);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Repair findById(Integer id) {
        return repairMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Repair repair) {
        int row = repairMapper.updateByPrimaryKeySelective(repair);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Repair repair = new Repair();
        repair.setId(id);
        repair.setStatus(status);
        int row = repairMapper.updateByPrimaryKeySelective(repair);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            repairMapper.deleteByPrimaryKey(id);
        }
        return true;
    }
}
