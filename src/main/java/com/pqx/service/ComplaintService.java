package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Community;
import com.pqx.pojo.Complaint;

import java.util.List;
import java.util.Map;

public interface ComplaintService {
    public List<Complaint> findAll();
    public Page<Complaint> search(Map searchMap);
    public Boolean add(Complaint complaint);
    public Complaint findById(Integer id);
    public Boolean update(Complaint complaint);
    public Boolean updateStatus(String status, Integer id);
    public Boolean del(List<Integer> ids);
}
