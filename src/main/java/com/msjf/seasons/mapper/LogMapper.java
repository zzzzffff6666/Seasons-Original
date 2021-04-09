package com.msjf.seasons.mapper;

import com.msjf.seasons.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface LogMapper {
    @Insert("insert into " +
            "log(uname, daytime, content) " +
            "values(#{uname}, #{daytime}, #{content})")
    int insert(Log log);

    @Select("select * " +
            "from log " +
            "where uname = #{uname}")
    List<Log> select(String uname);

    @Select("select * " +
            "from log " +
            "where daytime between #{begin} and #{end}")
    List<Log> selectByTime(Timestamp begin, Timestamp end);
}
