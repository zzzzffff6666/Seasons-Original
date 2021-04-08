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
            "email = #{email}, " +
            "birthday = #{birthday}, " +
            "space = #{space}, " +
            "sign = #{sign} " +
            "where id = #{id}")
    int updateInfo(User user);

    @Update("update user " +
            "set password = #{password} " +
            "where id = #{id}")
    int updatePassword(int id, String password);

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
