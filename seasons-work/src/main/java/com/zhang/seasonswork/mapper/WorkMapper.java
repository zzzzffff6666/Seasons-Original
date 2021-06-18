package com.zhang.seasonswork.mapper;

import com.zhang.seasonswork.model.Work;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WorkMapper {
    @Insert("insert into " +
            "work(title, type, tag, content, price, daytime, uid, laud, store, state, url) " +
            "values(#{title}, #{type}, #{tag}, #{content}, #{price}, #{daytime}, #{uid}, 0, 0, 0, #{url})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Work work);

    @Delete("delete " +
            "from work " +
            "where id = #{wid} " +
            "and uid = #{uid}")
    int delete(int wid, int uid);

    @Delete("delete " +
            "from work " +
            "where id = #{wid}")
    int deleteByAdmin(int wid);

    @Update("update work " +
            "set title = #{title}, " +
            "tag = #{tag}, " +
            "content = #{content}, " +
            "price = #{price} " +
            "where id = #{wid} " +
            "and uid = #{uid}")
    int updateInfo(int wid, int uid, String title, String tag, String content, float price);

    @Update("update work " +
            "set state = #{state} " +
            "where id = #{wid}")
    int updateState(int wid, int state);

    @Update("update work " +
            "set laud = laud + #{laud} " +
            "where id = #{wid}")
    int updateLaud(int wid, int laud);

    @Update("update work " +
            "set store = store + #{store} " +
            "where id = #{wid}")
    int updateStore(int wid, int store);

    @Update("update work " +
            "set url = #{url} " +
            "where id = #{wid}")
    int updateUrl(String url, int wid);

    @Select("select type " +
            "from work " +
            "where id = #{wid} " +
            "and state = 0")
    String selectForType(int wid);

    @Select("select * " +
            "from work " +
            "where id = #{wid} " +
            "and state = 0")
    @ResultType(value = Work.class)
    Work selectByID(int wid);

    @Select("select * " +
            "from work " +
            "where id = #{wid}")
    @ResultType(value = Work.class)
    Work selectApprovingByID(int wid);

    @Select("select * " +
            "from work " +
            "where uid = #{uid} " +
            "and state = 0 " +
            "order by daytime desc " +
            "limit #{offset}, #{amount}")
    @ResultType(value = Work.class)
    List<Work> selectByUid(int uid, int offset, int amount);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "order by daytime desc " +
            "limit #{offset}, #{amount}")
    List<Work> selectAll(int offset, int amount);

    @Select("select * " +
            "from work " +
            "order by daytime desc " +
            "limit #{offset}, #{amount}")
    List<Work> selectAdminAll(int offset, int amount);

    @Select("select * " +
            "from work " +
            "where state = 1 " +
            "order by daytime desc " +
            "limit #{offset}, #{amount}")
    @ResultType(value = Work.class)
    List<Work> selectByApproved(int offset, int amount);


    @Select("select * " +
            "from work " +
            "where (title like '%${pattern}%' " +
            "or tag like '%${pattern}%') " +
            "and state = 0 " +
            "order by daytime desc " +
            "limit #{offset}, #{amount}")
    @ResultType(value = Work.class)
    List<Work> selectByPattern(String pattern, int offset, int amount);

    @Select("select * " +
            "from work " +
            "where price between #{down} and #{up} " +
            "and state = 0 " +
            "order by daytime desc " +
            "limit #{offset}, #{amount}")
    @ResultType(value = Work.class)
    List<Work> selectByPrice(float up, float down, int offset, int amount);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "order by laud, daytime desc " +
            "limit #{offset}, #{amount}")
    @ResultType(value = Work.class)
    List<Work> selectByLaud(int offset, int amount);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "order by store, daytime desc " +
            "limit #{offset}, #{amount}")
    @ResultType(value = Work.class)
    List<Work> selectByStore(int offset, int amount);
}
