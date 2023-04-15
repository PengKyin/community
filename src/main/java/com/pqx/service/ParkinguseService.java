package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Community;
import com.pqx.pojo.ParkingUse;

import java.util.List;
import java.util.Map;

public interface ParkinguseService {
    public List<ParkingUse> findAll();
    public Page<ParkingUse> search(Map searchMap);
    public Boolean add(ParkingUse parkingUse);
    public ParkingUse findById(Integer id);
    public Boolean update(ParkingUse parkingUse);
    public Boolean updateStatus(String status, Integer id);
    public Boolean del(List<Integer> ids);
    public List<String> getParking(String communityName);
}
