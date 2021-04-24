package com.msjf.seasons.mapper;

import com.msjf.seasons.entity.Magazine;
import org.apache.ibatis.annotations.*;

public interface MagMapper {
    @Insert("insert into " +
            "magazine(name, password, license, address, email, phone) " +
            "values(#{name}, #{password}, #{license}, #{address}, #{email}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Magazine param);

    @Update("update magazine " +
            "set password = #{password} " +
            "where id = #{id}")
    int updatePassword(int id, String password);

    @Select("select * " +
            "from magazine " +
            "where email = #{email}")
    @ResultType(value = Magazine.class)
    Magazine select(String email);
}
