package com.pqx.service;


import com.github.pagehelper.Page;
import com.pqx.pojo.House;

import java.util.List;
import java.util.Map;

public interface HouseService {
    public List<House> findAll();

    public Page<House> search(Map searchMap);

    public Boolean add(House house);

    House findById(Integer id);

    public Boolean update(House house);

    Boolean del(List<Integer> ids);

    List<String> getOptions();

    List<String> getBuildingName(House house);
}
