package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.ChargeDetailMapper;
import com.pqx.pojo.ChargeDetail;
import com.pqx.service.ChargeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ChargeDetailServiceImpl implements ChargeDetailService {
    @Autowired
    private ChargeDetailMapper chargeDetailMapper;

    @Override
    public List<ChargeDetail> findAll() {
        List<ChargeDetail> chargeDetails = chargeDetailMapper.selectAll();
        return chargeDetails;
    }

    @Override
    public Page<ChargeDetail> search(Map searchMap) {
        Example example = new Example(ChargeDetail.class);
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
        Page<ChargeDetail> chargeDetails = (Page<ChargeDetail>) chargeDetailMapper.selectByExample(example);
        return chargeDetails;
    }

    @Override
    public Boolean add(ChargeDetail chargeDetail) {
        int row = chargeDetailMapper.insert(chargeDetail);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ChargeDetail findById(Integer id) {
        return chargeDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(ChargeDetail chargeDetail) {
        int row = chargeDetailMapper.updateByPrimaryKeySelective(chargeDetail);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            chargeDetailMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

}
