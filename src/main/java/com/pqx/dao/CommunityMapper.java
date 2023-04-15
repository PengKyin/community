package com.pqx.dao;

import com.pqx.pojo.Community;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommunityMapper extends Mapper<Community> {
    @Select("select name from tb_community")
    List<String> getCommunityName();
}
