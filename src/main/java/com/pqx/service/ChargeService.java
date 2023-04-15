package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Charge;
import com.pqx.pojo.Community;

import java.util.List;
import java.util.Map;

public interface ChargeService {
    public List<Charge> findAll();
    public Page<Charge> search(Map searchMap);
    public Boolean add(Charge charge);
    public Charge findById(Integer id);
    public Boolean update(Charge charge);
    public Boolean updateStatus(String status, Integer id);
    public Boolean del(List<Integer> ids);
}
