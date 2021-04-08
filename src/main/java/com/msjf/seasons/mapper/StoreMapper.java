package com.msjf.seasons.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StoreMapper {
    @Insert("insert into " +
            "store(uid, wid) " +
            "values(#{uid}, #{wid})")
    int add(int uid, int wid);

    @Delete("delete from store " +
            "where uid = #{uid} " +
            "and wid = #{wid}")
    int delete(int uid, int wid);

    @Select("select wid " +
            "from store " +
            "where uid = #{uid}")
    @ResultType(value = Integer.class)
    List<Integer> selectByUser(int uid);
}
