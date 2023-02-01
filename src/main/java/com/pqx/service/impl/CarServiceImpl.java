package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.CarMapper;
import com.pqx.pojo.Car;
import com.pqx.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarMapper carMapper;

    @Override
    public List<Car> findAll() {
        List<Car> cars = carMapper.selectAll();
        return cars;
    }

    @Override
    public Page<Car> search(Map searchMap) {
        Example example = new Example(Car.class);
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
            if (StringUtil.isNotEmpty((String) searchMap.get("carNumber"))){
                criteria.andLike("carNumber", "%"+(String) searchMap.get("carNumber")+"%");
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
        Page<Car> cars = (Page<Car>) carMapper.selectByExample(example);
        return cars;
    }

    @Override
    public Boolean add(Car car) {
        int row = carMapper.insert(car);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Car findById(Integer id) {
        return carMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Car car) {
        int row = carMapper.updateByPrimaryKeySelective(car);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            carMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    @Override
    public List<String> getOwner() {
        List<String> owner = carMapper.getOwner();
        return owner;
    }

    @Override
    public Integer getOwnerId(String name) {
        Integer ownerId = carMapper.getOwnerName(name);
        return ownerId;
    }
}
