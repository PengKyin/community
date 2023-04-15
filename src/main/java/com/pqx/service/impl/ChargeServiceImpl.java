package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.ChargeMapper;
import com.pqx.pojo.Charge;
import com.pqx.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;
@Service
public class ChargeServiceImpl implements ChargeService {
    @Autowired
    private ChargeMapper chargeMapper;

    @Override
    public List<Charge> findAll() {
        List<Charge> charges = chargeMapper.selectAll();
        return charges;
    }

    @Override
    public Page<Charge> search(Map searchMap) {
        Example example = new Example(Charge.class);
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
            if (searchMap.get("pageNum") != null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if (searchMap.get("pageSize") != null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<Charge> charges = (Page<Charge>) chargeMapper.selectByExample(example);
        return charges;
    }

    @Override
    public Boolean add(Charge charge) {
        int row = chargeMapper.insert(charge);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Charge findById(Integer id) {
        return chargeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Charge charge) {
        int row = chargeMapper.updateByPrimaryKeySelective(charge);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Charge charge = new Charge();
        charge.setId(id);
        charge.setStatus(status);
        int row = chargeMapper.updateByPrimaryKeySelective(charge);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            chargeMapper.deleteByPrimaryKey(id);
        }
        return true;
    }
}
