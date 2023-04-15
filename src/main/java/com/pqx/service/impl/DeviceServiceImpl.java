package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.DeviceMapper;
import com.pqx.pojo.Device;
import com.pqx.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public List<Device> findAll() {
        List<Device> devices = deviceMapper.selectAll();
        return devices;
    }

    @Override
    public Page<Device> search(Map searchMap) {
        Example example = new Example(Device.class);
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
        Page<Device> devices = (Page<Device>) deviceMapper.selectByExample(example);
        return devices;
    }

    @Override
    public Boolean add(Device device) {
        int row = deviceMapper.insert(device);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Device findById(Integer id) {
        return deviceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Device device) {
        int row = deviceMapper.updateByPrimaryKeySelective(device);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            deviceMapper.deleteByPrimaryKey(id);
        }
        return true;
    }
}
