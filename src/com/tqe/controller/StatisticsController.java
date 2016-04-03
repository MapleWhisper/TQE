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
        CourseModel courseModel = new CourseModel(course);
        Batches batch = batchesService.getById(bid);
        if(batch==null){
            return sendError(model,"没有找到指定的批次! cid:"+cid+" cno:"+cno+" batchId:"+bid,logger);
        }

        List<Batches> batchesList = batchesService.findAllBySeason(course.getSeason());	//默认得到课程所在学期的所有批次

        for(Batches b : batchesList){	//遍历所有得到的批次列表
            List<StuResultTable> stuTableList = evalService.findAllStuTableByCidAndBid(cid, cno, b.getId());
            List<TeaResultTable> teaTableList = evalService.findAllTeaTableByCidAndBid(cid, cno, b.getId());
            List<LeaResultTable> leaTableList = evalService.findAllLeaTableByCidAndBid(cid, cno, b.getId());
            CourseModel.Batches batches = new CourseModel.Batches();
            batches.setStuTableList(stuTableList);
            batches.setTeaTableList(teaTableList);
            batches.setLeaTableList(leaTableList);
            batches.setBatches(b);
            courseModel.getBatchesList().add(batches);
        }
        courseBatchService.reAnalyseCourseBatch(cid, cno, bid);
        CourseBatch courseBatch = courseBatchService.getByIdWithQuestList(cid, cno, bid);
        courseBatch.setBatch(batch);
        model.addAttribute("course",course);
        model.addAttribute("courseModel", courseModel);
        model.addAttribute("courseBatch",courseBatch);

        return "course/courseStatistics";
    }
}
