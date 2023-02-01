package com.pqx.dao;

import com.pqx.pojo.House;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HouseMapper extends Mapper<House> {

    @Select("select name from tb_community")
    List<String> getOption();

    @Select("select name from tb_building where community_name = #{buildingName}")
    List<String> getBuildingName(String buildingName);

}
