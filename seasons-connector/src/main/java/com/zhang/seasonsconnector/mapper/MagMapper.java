package com.zhang.seasonsconnector.mapper;

import com.zhang.seasonsconnector.model.Magazine;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MagMapper {
    @Insert("insert into " +
            "magazine(name, password, license, address, email, phone, locked) " +
            "values(#{name}, #{password}, #{license}, #{address}, #{email}, #{phone}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Magazine param);

    @Delete("delete " +
            "from magazine " +
            "where id = #{id}")
    int delete(int id);

    @Update("update magazine " +
            "set name = #{name}, " +
            "license = #{license}, " +
            "address = #{address}, " +
            "email = #{email}, " +
            "phone = #{phone} " +
            "where id = #{id}")
    int updateInfo(Magazine param);

    @Update("update magazine " +
            "set password = #{password} " +
            "where id = #{id}")
    int updatePassword(int id, String password);

    @Update("update magazine " +
            "set locked = #{locked} " +
            "where id = #{id}")
    int updateLocked(int id, int locked);

    @Select("select id, name, license, address, email, phone " +
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
