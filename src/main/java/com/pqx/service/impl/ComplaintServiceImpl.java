package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.ComplaintMapper;
import com.pqx.pojo.Complaint;
import com.pqx.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    @Autowired
    private ComplaintMapper complaintMapper;

    @Override
    public List<Complaint> findAll() {
        List<Complaint> complaints = complaintMapper.selectAll();
        return complaints;
    }

    @Override
    public Page<Complaint> search(Map searchMap) {
        Example example = new Example(Complaint.class);
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
            if (StringUtil.isNotEmpty((String) searchMap.get("reason"))){
                criteria.andLike("reason", "%"+(String) searchMap.get("reason")+"%");
            }
            if (searchMap.get("pageNum") != null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if (searchMap.get("pageSize") != null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<Complaint> complaints = (Page<Complaint>) complaintMapper.selectByExample(example);
        return complaints;
    }

    @Override
    public Boolean add(Complaint complaint) {
        int row = complaintMapper.insert(complaint);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Complaint findById(Integer id) {
        return complaintMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Complaint complaint) {
        int row = complaintMapper.updateByPrimaryKeySelective(complaint);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Complaint complaint = new Complaint();
        complaint.setId(id);
        complaint.setStatus(status);
        int row = complaintMapper.updateByPrimaryKeySelective(complaint);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            complaintMapper.deleteByPrimaryKey(id);
        }
        return true;
    }
}
