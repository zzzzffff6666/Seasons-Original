package com.zhang.seasonsconnector.mapper;

import com.zhang.seasonsconnector.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into " +
            "user(name, password, email, coin, locked) " +
            "values(#{name}, #{password}, #{email}, 0, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

    @Delete("delete " +
            "from user " +
            "where id = #{id}")
    int delete(int id);

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

    @Update("update user " +
            "set locked = #{locked} " +
            "where id = #{id}")
    int updateLocked(int id, int locked);

    @Select("select id, name, email, birthday, space, sign, coin " +
            "from user " +
            "where id = #{id}")
    @ResultType(value = User.class)
    User selectByID(int id);

    @Select("select id, name, password, email, phone, locked " +
            "from user " +
            "where name = #{param} " +
            "or email = #{param} " +
            "or phone = #{param}")
    @ResultType(value = User.class)
    User selectByOthers(String param);

    @Select("select id, name, password, email, phone, locked " +
            "from user " +
            "where name = #{name}")
    @ResultType(value = User.class)
    User selectByName(String name);

    @Select("select id, name, password, email, phone, locked " +
            "from user " +
            "where email = #{email}")
    @ResultType(value = User.class)
    User selectByEmail(String email);

    @Select("select id, name, password, email, phone, locked " +
            "from user " +
            "where phone = #{phone}")
    @ResultType(value = User.class)
    User selectByPhone(String phone);
}
