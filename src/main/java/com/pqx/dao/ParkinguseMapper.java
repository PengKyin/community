package com.pqx.dao;

import com.pqx.pojo.ParkingUse;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ParkinguseMapper extends Mapper<ParkingUse> {
    @Select("select code from tb_parking where community_name = #{communityName} and status = '1'")
    List<String> getParking(String communityName);
}
