package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Owner;

import java.util.List;
import java.util.Map;

public interface OwnerService {
    public List<Owner> findAll();
    public Page<Owner> search(Map searchMap);
    public Boolean add(Owner owner);

    Owner findById(Integer id);

    public Boolean update(Owner owner);

    Boolean del(List<Integer> ids);

    List<Integer> getCommunity();

    List<Integer> getHouse(Integer communityId);
}
