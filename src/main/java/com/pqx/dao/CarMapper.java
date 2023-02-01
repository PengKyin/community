package com.pqx.dao;

import com.pqx.pojo.Car;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CarMapper extends Mapper<Car> {
    @Select("select name from tb_owner")
    List<String> getOwner();

    @Select("select id from tb_owner where name = #{name}")
    Integer getOwnerName(String name);
}
