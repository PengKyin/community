package com.pqx.dao;

import com.pqx.pojo.Owner;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OwnerMapper extends Mapper<Owner> {

    @Select("select id from tb_community")
    List<Integer> getCommunity();

    @Select("select building_id from tb_house where community_id = #{communityId}")
    List<Integer> getHouse(Integer communityId);
}
