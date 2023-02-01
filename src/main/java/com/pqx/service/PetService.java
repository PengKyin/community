package com.pqx.service;

import com.github.pagehelper.Page;
import com.pqx.pojo.Pet;

import java.util.List;
import java.util.Map;

public interface PetService {
    public List<Pet> findAll();
    public Page<Pet> search(Map searchMap);
    public Boolean add(Pet pet);
    public Pet findById(Integer id);
    public Boolean update(Pet pet);
    public Boolean del(List<Integer> ids);
}
