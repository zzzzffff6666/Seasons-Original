package com.msjf.seasons.mapper;

import com.msjf.seasons.entity.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Insert("insert into " +
            "user(name, password, email, coin) " +
            "values(#{name}, #{password}, #{email}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

    @Update("update user " +
            "set name = #{name}, " +
            "email = #{email}, " +
            "birthday = #{birthday}, " +
            "space = #{space}, " +
            "sign = #{sign}, " +
            "where id = #{id}")
    int updateInfo(User user);

    @Update("update user " +
            "set password = #{password} " +
            "where id = #{id}")
    int updatePassword(int id, String password);

    @Update("update user " +
            "set coin = coin + #{change} " +
            "where id = #{id}")
    int updateCoin(int id, float change);

    @Select("select id, name, email, birthday, space, sign, coin " +
            "from user " +
            "where id = #{id}")
    @ResultType(value = User.class)
    User searchByID(int id);

    @Select("select id, name, password " +
            "from user " +
            "where name = #{param} " +
            "or email = #{param}")
    @ResultType(value = User.class)
    User searchByName(String param);
}
