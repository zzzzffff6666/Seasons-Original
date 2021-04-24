package com.msjf.seasons.mapper;

import com.msjf.seasons.entity.Admin;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

public interface AdminMapper {
    @Select("select * " +
            "from admin " +
            "where name = #{name}")
    @ResultType(value = Admin.class)
    Admin select(String name);
}
