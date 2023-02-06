package com.pqx.dao;

import com.pqx.pojo.Parking;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ParkingMapper extends Mapper<Parking> {
    @Select("select name from tb_community")
    List<String> getCommunityName();

    @Select("select id from tb_community where name = #{communityName}")
    Integer getCommunityId(String communityName);
}
