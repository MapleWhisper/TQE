package com.tqe.dao;

import com.tqe.po.CourseBatch;
import com.tqe.po.StudentSeason;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentSeasonDao {

    @Select("select * from studentSeason ss where ss.sid = #{sid} and ss.season = #{season} ")
    StudentSeason getById(@Param("sid") String sid, @Param("season")String season);

    @Insert("INSERT INTO `studentseason` (`sid`, `season`, `avgScore`, `avgScoreList`, `levelCnts` ,`resultTableNum` , `resultTableJsonString` ,`mtime` ,`questionListString`) " +
            "VALUES (#{sid}, #{season},#{avgScore}, #{avgScoreList}, #{levelCnts} , #{resultTableNum} , #{resultTableJsonString} ,now() , #{questionListString} );")
    void save(StudentSeason stuSeason);

    @Update("update `studentseason` set avgScore = #{avgScore} ,avgScoreList = #{avgScoreList} ,levelCnts = #{levelCnts} ," +
            " resultTableNum = #{resultTableNum} , resultTableJsonString = #{resultTableJsonString} , mtime = now() ," +
            " questionListString = #{questionListString} where sid = #{sid} and season = #{season}")
    void update(StudentSeason stuSeason);
}
