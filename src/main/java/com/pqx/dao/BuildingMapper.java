package com.pqx.dao;

import com.pqx.pojo.Building;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BuildingMapper extends Mapper<Building>{
    @Select("select name from tb_building where community_name = #{communityName}")
    List<String> getBuildingName(String communityName);

    @Select("select id from tb_building where name = #{buildingName}")
    Integer getBuildingId(String buildingName);
}
