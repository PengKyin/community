package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Repair;

import java.util.List;
import java.util.Map;

public interface RepairService {
    public List<Repair> findAll();
    public Page<Repair> search(Map searchMap);
    public Boolean add(Repair repair);
    public Repair findById(Integer id);
    public Boolean update(Repair repair);
    public Boolean updateStatus(String status, Integer id);
    public Boolean del(List<Integer> ids);
}
