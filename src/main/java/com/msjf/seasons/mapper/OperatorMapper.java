package com.msjf.seasons.mapper;

import com.msjf.seasons.entity.Operator;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

public interface OperatorMapper {
    @Select("select * " +
            "from operator " +
            "where name = #{name}")
    @ResultType(value = Operator.class)
    Operator select(String name);
}
