package com.msjf.seasons.mapper;

import com.msjf.seasons.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Insert("insert into " +
            "user(name, password, email, birthday, space, sign) " +
            "values(#{name}, #{password}, #{email}, #{birthday}, #{space}, #{sign})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int add(User user);

    @Update("update user " +
            "set name = #{name}, " +
            "password = #{password}, " +
            "email = #{email}, " +
            "birthday = #{birthday}, " +
            "space = #{space}, " +
            "sign = #{sign} " +
            "where id = #{id}")
    int update(User user);

    @Select("select * " +
            "from user " +
            "where id = #{id}")
    @ResultType(value = User.class)
    User searchByID(int id);

    @Select("select * " +
            "from user " +
            "where name = #{name}")
    @ResultType(value = User.class)
    User searchByName(String name);
}
