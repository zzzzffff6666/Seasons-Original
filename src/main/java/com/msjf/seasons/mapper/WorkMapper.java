package com.msjf.seasons.mapper;

import com.msjf.seasons.entity.Work;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WorkMapper {
    @Insert("insert into " +
            "work(type, daytime, uid, content, laud, store, tag, state) " +
            "values(#{type}, #{daytime}, #{uid}, #{content}, #{laud}, #{store}, #{tag}, #{state})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int add(Work work);

    @Delete("delete from work " +
            "where id = #{id}")
    int deleteByID(int id);

    @Delete("delete from work " +
            "where uid = #{uid}")
    int deleteByUID(int uid);

    @Update("update work " +
            "set content = #{content} " +
            "where id = #{id}")
    int updateContent(int id, String content);

    @Update("update work " +
            "set laud = laud + #{num} " +
            "where id = #{id}")
    int updateLaud(int id, int num);

    @Update("update work " +
            "set store = store + #{num} " +
            "where id = #{id}")
    int updateStore(int id, int num);

    @Update("update work " +
            "set tag = #{tag} " +
            "where id = #{id}")
    int updateTag(int id, String tag);

    @Update("update work " +
            "set state = #{state} " +
            "where id = #{id}")
    int updateState(int id, int state);

    @Select("select * " +
            "from work " +
            "where id = #{id}")
    @ResultType(Work.class)
    Work searchByID(int id);

    @Select("select * " +
            "from work")
    @ResultType(Work.class)
    List<Work> searchAll();

    @Select("select * " +
            "from work " +
            "where uid = #{uid}")
    @ResultType(Work.class)
    List<Work> searchByUID(int uid);

    @Select("select * " +
            "from work " +
            "where state = #{state}")
    @ResultType(Work.class)
    List<Work> searchByState(int state);

    @Select("select * " +
            "from work " +
            "where tag like '%${tag}%'")
    List<Work> searchByTag(String tag);
}
