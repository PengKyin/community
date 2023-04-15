package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Device;

import java.util.List;
import java.util.Map;

public interface DeviceService {
    public List<Device> findAll();
    public Page<Device> search(Map searchMap);
    public Boolean add(Device device);
    public Device findById(Integer id);
    public Boolean update(Device device);
    public Boolean del(List<Integer> ids);
}
