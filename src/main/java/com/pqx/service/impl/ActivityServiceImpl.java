package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.ActivityMapper;
import com.pqx.pojo.Activity;
import com.pqx.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = activityMapper.selectAll();
        return activities;
    }

    @Override
    public Page<Activity> search(Map searchMap) {
        Example example = new Example(Activity.class);
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
            if (StringUtil.isNotEmpty((String) searchMap.get("title"))){
                criteria.andLike("title", "%"+(String) searchMap.get("title")+"%");
            }
            if (searchMap.get("pageNum") != null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if (searchMap.get("pageSize") != null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<Activity> activities = (Page<Activity>) activityMapper.selectByExample(example);
        return activities;
    }

    @Override
    public Boolean add(Activity activity) {
        int row = activityMapper.insert(activity);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Activity findById(Integer id) {
        return activityMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Activity activity) {
        int row = activityMapper.updateByPrimaryKeySelective(activity);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean updateStatus(String status, Integer id) {
        Activity activity = new Activity();
        activity.setId(id);
        int row = activityMapper.updateByPrimaryKeySelective(activity);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            activityMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

}
