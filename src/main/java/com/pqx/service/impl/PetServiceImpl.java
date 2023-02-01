package com.pqx.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pqx.dao.PetMapper;
import com.pqx.pojo.Pet;
import com.pqx.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetMapper petMapper;

    @Override
    public List<Pet> findAll() {
        List<Pet> pets = petMapper.selectAll();
        return pets;
    }

    @Override
    public Page<Pet> search(Map searchMap) {
        Example example = new Example(Pet.class);
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
            if (StringUtil.isNotEmpty((String) searchMap.get("color"))){
                criteria.andLike("color", "%"+(String) searchMap.get("color")+"%");
            }
            if (searchMap.get("pageNum") != null){
                pageNum = (Integer) searchMap.get("pageNum");
            }
            if (searchMap.get("pageSize") != null){
                pageSize = (Integer) searchMap.get("pageSize");
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        Page<Pet> pets = (Page<Pet>) petMapper.selectByExample(example);
        return pets;
    }

    @Override
    public Boolean add(Pet pet) {
        int row = petMapper.insert(pet);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Pet findById(Integer id) {
        return petMapper.selectByPrimaryKey(id);
    }

    @Override
    public Boolean update(Pet pet) {
        int row = petMapper.updateByPrimaryKeySelective(pet);
        if (row > 0){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public Boolean del(List<Integer> ids) {
        for(Integer id:ids){
            petMapper.deleteByPrimaryKey(id);
        }
        return true;
    }
}
