package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Community;
import com.pqx.pojo.Owner;
import com.pqx.pojo.Parking;

import javax.servlet.http.PushBuilder;
import java.util.List;
import java.util.Map;

public interface ParkingService {
    public List<Parking> findAll();
    public Page<Parking> search(Map searchMap);
    public Boolean add(Parking parking);
    public Parking findById(Integer id);
    public Boolean update(Parking parking);
    public Boolean updateStatus(String status, Integer id);
    public Boolean del(List<Integer> ids);
    public List<String> getCommunityName();
    public Integer getCommunityId(String communityName);

}
