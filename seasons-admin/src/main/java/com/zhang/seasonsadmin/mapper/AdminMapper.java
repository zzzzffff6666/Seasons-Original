package com.zhang.seasonsadmin.mapper;

import com.zhang.seasonsadmin.model.Admin;
import org.apache.ibatis.annotations.*;

public interface AdminMapper {
    @Insert("insert into " +
            "admin(name, password, email, phone, locked) " +
            "values(#{name}, #{password}, #{email}, #{phone}, 0)")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Admin param);

    @Insert("delete " +
            "from admin " +
            "where id = #{id}")
    int delete(int id);

    @Update("update admin " +
            "set name = #{name}, " +
            "email = #{email}, " +
            "phone = #{phone} " +
            "where id = #{id}")
    int updateInfo(Admin admin);

    @Update("update admin " +
            "set password = #{password} " +
            "where id = #{id}")
    int updatePassword(int id, String password);

    @Update("update admin " +
            "set locked = #{locked} " +
            "where id = #{id}")
    void updateLocked(int id, int locked);

    @Select("select id, name, email " +
            "from admin " +
            "where id = #{id}")
    @ResultType(value = Admin.class)
    Admin selectByID(int id);

    @Select("select password " +
            "from admin " +
            "where id = #{id}")
    String selectPasswordByID(int id);

    @Select("select id, name, password, email, phone, locked " +
            "from admin " +
            "where name = #{param} " +
            "or email = #{param} " +
            "or phone = #{param}")
    @ResultType(value = Admin.class)
    Admin selectByOthers(String param);

    @Select("select id, name, password, email, phone, locked " +
            "from admin " +
            "where name = #{name}")
    @ResultType(value = Admin.class)
    Admin selectByName(String name);

    @Select("select id, name, password, email, phone, locked " +
            "from admin " +
            "where email = #{email}")
    @ResultType(value = Admin.class)
    Admin selectByEmail(String email);

    @Select("select id, name, password, email, phone, locked " +
            "from admin " +
            "where phone = #{phone}")
    @ResultType(value = Admin.class)
    Admin selectByPhone(String phone);
}
