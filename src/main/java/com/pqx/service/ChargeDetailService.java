package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.ChargeDetail;

import java.util.List;
import java.util.Map;

public interface ChargeDetailService {
    public List<ChargeDetail> findAll();
    public Page<ChargeDetail> search(Map searchMap);
    public Boolean add(ChargeDetail chargeDetail);
    public ChargeDetail findById(Integer id);
    public Boolean update(ChargeDetail chargeDetail);
    public Boolean del(List<Integer> ids);
}
