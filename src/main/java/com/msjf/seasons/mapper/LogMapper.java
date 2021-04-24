package com.msjf.seasons.mapper;

import com.msjf.seasons.entity.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface LogMapper {
    @Insert("insert into " +
            "log(type, daytime, content) " +
            "values(#{type}, #{daytime}, #{content})")
    int insert(Log log);

    @Select("select * " +
            "from log " +
            "where type = #{type}")
    @ResultType(value = Log.class)
    List<Log> selectByType(int type);

    @Select("select * " +
            "from log " +
            "where daytime between #{begin} and #{end}")
    List<Log> selectByTime(Timestamp begin, Timestamp end);
}
