package com.zhang.seasonsuser.mapper;

import com.zhang.seasonsuser.model.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {
    @Insert("insert into " +
            "user(name, password, email, phone, locked, birthday, sign, coin) " +
            "values(#{name}, #{password}, #{email}, #{phone}, 0, #{birthday}, #{sign}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

    @Delete("delete " +
            "from user " +
            "where id = #{id}")
    int delete(int id);

    @Update("update user " +
            "set name = #{name}, " +
            "email = #{email}, " +
            "phone = #{phone}" +
            "birthday = #{birthday}, " +
            "sign = #{sign} " +
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
    void updateLocked(int id, int locked);

    @Select("select locked " +
            "from user " +
            "where id = #{id}")
    int selectLockedByID(int id);

    @Select("select password " +
            "from user " +
            "where id = #{id}")
    String selectPasswordByID(int id);

    @Select("select coin " +
            "form user " +
            "where id = #{id}")
    float selectCoinByID(int id);

    @Select("select id, name, email, phone, birthday, sign, coin " +
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
