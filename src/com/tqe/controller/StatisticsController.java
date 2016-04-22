package com.tqe.controller;

import com.tqe.model.CourseModel;
import com.tqe.po.*;
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
     * 显示 课程的统计图
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




}
