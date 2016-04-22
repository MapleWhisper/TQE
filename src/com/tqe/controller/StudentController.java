package com.tqe.controller;
import java.util.*;

import com.tqe.base.BaseResult;
import com.tqe.base.enums.DepartmentType;
import com.tqe.base.vo.PageVO;
import com.tqe.po.StudentSeason;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tqe.model.CourseModel;
import com.tqe.po.Batches;
import com.tqe.po.Student;
import com.tqe.po.TeaStuResultTable;
import com.tqe.utils.SystemUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

@Controller()
@RequestMapping("/admin")
public class StudentController extends BaseController{

    private static final Log logger = LogFactory.getLog(StudentController.class);

	/**
	 * 显示学生数据界面
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/student"},method={RequestMethod.GET})
	public String student(
            Model model,
            HttpSession session
    ){

		model.addAttribute("departmentList", departmentService.findAvailableDepartmentList(DepartmentType.STUDENT));

		return "student/student";
	}
	
	/**
	 * 显示学生数据界面 关键字查找
	 * @param model
	 * @return
	 */
	@RequestMapping(value={"/student"},method={RequestMethod.POST})
	public String searchStudent(
			Model model,
			PageVO pageVO){
		model.addAttribute("departmentList", departmentService.findAvailableDepartmentList(DepartmentType.STUDENT));
		model.addAttribute("condition", pageVO.getFilters());
		model.addAttribute("studentList", studentService.findByPageVO(pageVO));
		return "student/student";
	}

    /**
     * 显示学生详情
     */
	@RequestMapping("/student/show")
	public String showStudent(
            @RequestParam() String sid,
            @RequestParam(required = false) String season,
            Model model
    ){
		Student stu = studentService.getById(sid);	//获取学生信息
		if(stu==null){
            return sendError(model,"没有找到学生信息! sid="+sid,logger);
        }
		CourseModel courseModel = new CourseModel();
        if(StringUtils.isBlank(season)){    //默认显示当前学期的 学生收到的评价批次
            season = SystemUtils.getSeason();
        }
		List<Batches> batchesList = batchesService.findAllBySeason(season);	//得到所有的批次
		
		for(Batches b : batchesList){	//遍历所有得到的批次列表
            PageVO pageVO = new PageVO();
            pageVO.getFilters().put("sid",sid);
            pageVO.getFilters().put("bid",b.getId()+"");
			List<TeaStuResultTable> teaStuTableList = evalService.findTeaStuResultTable(pageVO,true);
			CourseModel.Batches batches = new CourseModel.Batches();
			batches.setTeaStuTableList(teaStuTableList);
			batches.setBatches(b);
			courseModel.getBatchesList().add(batches);
		}
		model.addAttribute("student", stu);
		model.addAttribute("courseModel", courseModel);

        studentService.reAnalyseStudentStatistics(sid);
        Map<String,String> condition = new HashMap<String,String>();    //记住前台的选择
        condition.put("season",season);
        model.addAttribute("condition",condition);
		return "student/showStudent";
	}

    /**
     * 返回学生一个学期的统计信息
     */
    @RequestMapping(value={"/student-season/info"})
    @ResponseBody()
    public BaseResult stuSeasonInfo(
        @RequestParam() String sid,
        @RequestParam() String season
    ){

        StudentSeason studentSeason = studentSeasonService.getById(sid,season);
        studentSeasonService.reAnalyseStuSeason(sid,season);
        if(studentSeason==null){
            return BaseResult.createFailure("没有找到相应的记录！");
        }
        return BaseResult.createSuccess(studentSeason);

    }
	
	@RequestMapping("/student/add")
	public String addStudent(){
		return "student/addStudent";
	}
	
	@RequestMapping("/student/save")
	public String saveStudent(){
		return "redirect:/admin/student";
	}
	
	
}
