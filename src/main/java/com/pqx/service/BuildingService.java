package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Building;

import java.util.List;
import java.util.Map;

public interface BuildingService {

    public List<Building> findAll();

    public Page<Building> search(Map searchMap);

    public Boolean add(Building building);

    Building findById(Integer id);

    public Boolean update(Building building);

    Boolean del(List<Integer> ids);
}
