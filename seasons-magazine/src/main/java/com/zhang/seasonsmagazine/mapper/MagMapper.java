package com.zhang.seasonsmagazine.mapper;

import com.zhang.seasonsmagazine.model.Magazine;
import org.apache.ibatis.annotations.*;

public interface MagMapper {
    @Insert("insert into " +
            "magazine(name, password, email, phone, locked, company, license, address) " +
            "values(#{name}, #{password}, #{email}, #{phone}, 0, #{company}, #{license}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Magazine param);

    @Delete("delete " +
            "from magazine " +
            "where id = #{id}")
    int delete(int id);

    @Update("update magazine " +
            "set name = #{name}, " +
            "email = #{email}, " +
            "phone = #{phone}, " +
            "company = #{company}, " +
            "license = #{license}, " +
            "address = #{address} " +
            "where id = #{id}")
    int updateInfo(Magazine param);

    @Update("update magazine " +
            "set password = #{password} " +
            "where id = #{id}")
    int updatePassword(int id, String password);

    @Update("update magazine " +
            "set locked = #{locked} " +
            "where id = #{id}")
    void updateLocked(int id, int locked);

    @Select("select locked " +
            "form magazine " +
            "where id = {id}")
    int selectLockedByID(int id);

    @Select("select password " +
            "form magazine " +
            "where id = {id}")
    String selectPasswordByID(int id);

    @Select("select id, name, email, phone, company, license, address " +
            "from magazine " +
            "where id = #{id}")
    @ResultType(value = Magazine.class)
    Magazine selectByID(int id);

    @Select("select id, name, password, email, phone, locked " +
            "from magazine " +
            "where name = #{param} " +
            "or email = #{param} " +
            "or phone = #{param}")
    @ResultType(value = Magazine.class)
    Magazine selectByOthers(String param);

    @Select("select id, name, password, email, phone, locked " +
            "from magazine " +
            "where name = #{name}")
    @ResultType(value = Magazine.class)
    Magazine selectByName(String name);

    @Select("select id, name, password, email, phone, locked " +
            "from magazine " +
            "where email = #{email}")
    @ResultType(value = Magazine.class)
    Magazine selectByEmail(String email);

    @Select("select id, name, password, email, phone, locked " +
            "from magazine " +
            "where phone = #{phone}")
    @ResultType(value = Magazine.class)
    Magazine selectByPhone(String phone);
}
