package com.tqe.controller;

import com.tqe.model.CourseModel;
import com.tqe.po.*;
import com.tqe.vo.TeacherVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Maple on 2016/3/23.
 */
@Controller()
@RequestMapping("/admin")
public class StatisticsController extends  BaseController{

    Log logger = LogFactory.getLog(StatisticsController.class);

    /**
     * 显示 课程的统计页面
     */
    @RequestMapping(value={"/statistics/course"})
    public String statisticsCourse(
            Model model,
            @RequestParam() String cid,
            @RequestParam() Integer cno,
            Integer bid
    ){

        Course course = courseService.getAllById(cid,cno);
        if(course == null){
            return sendError(model,"没有找到指定的课程！ cid:"+cid+"  cno:"+cno,logger);
        }

        Batches batch = batchesService.getById(bid);
        if(batch==null){
            return sendError(model,"没有找到指定的批次! cid:"+cid+" cno:"+cno+" batchId:"+bid,logger);
        }

        CourseModel courseModel =courseService.buildCourseModel(cid, cno, course);

        courseBatchService.reAnalyseCourseBatch(cid, cno, bid);
        CourseBatch courseBatch = courseBatchService.getByIdWithQuestList(cid, cno, bid);
        courseBatch.setBatch(batch);
        model.addAttribute("course",course);
        model.addAttribute("courseModel", courseModel);
        model.addAttribute("courseBatch",courseBatch);

        return "course/courseStatistics";
    }

    /**
     * 显示 教师统计页面
     */
    @RequestMapping(value={"/statistics/teacher"})
    public String statisticsTeacher(
            Model model,
            @RequestParam() String tid
    ){
        Teacher tea = teacherService.getById(tid);
        if(tea==null){
            return sendError(model,"没有找到指定的教师信息。 tid:"+tid ,logger);
        }

        model.addAttribute("teacher",tea);

        return "teacher/teacherStatistics";
    }

    /**
     * 显示 学生统计页面
     */
    @RequestMapping(value={"/statistics/student"})
    public String statisticsStudent(
            Model model,
            @RequestParam() String sid
    ){
        Student student = studentService.getById(sid);
        if(student==null){
            return sendError(model,"没有找到指定的学生信息。 sid:"+sid ,logger);
        }

        model.addAttribute("student",student);

        return "student/studentStatistics";
    }







}
