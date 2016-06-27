package com.tqe.controller;



import java.util.List;

import com.tqe.base.BaseResult;
import com.tqe.base.enums.DepartmentType;
import com.tqe.base.vo.PageVO;
import com.tqe.po.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tqe.model.CourseModel;

/**
 * 课程类
 * 
 * @author 于广路
 *
 */
@Controller()
@RequestMapping("/admin")
public class CourseController extends BaseController{
	
	Log logger = LogFactory.getLog(CourseController.class);

	/**
	 * 显示课程列表页面
	 * @return
	 */
	@RequestMapping(value="/course",method=RequestMethod.GET)
	public String course(Model model){
		model.addAttribute("departmentList", departmentService.findAvailableDepartmentList(DepartmentType.COURSE));
		return "course/course";				//直接返回  前缀加 字符串+jsp的页面
	}
	
	/**
	 * 根据条件查询 课程列表
	 * @return
	 */
	@RequestMapping(value="/course",method=RequestMethod.POST)
	public String course1(
            Model model,
            PageVO pageVO
    ){
		model.addAttribute("condition", pageVO.getFilters());
		model.addAttribute("departmentList", departmentService.findAvailableDepartmentList(DepartmentType.COURSE));
		List<Course> list = courseService.findByCondition(pageVO);
		model.addAttribute("courseList",list);
		return "course/course";				//直接返回  前缀加 字符串+jsp的页面
	}
	
	/**
	 * 显示课程评教详情
	 * @return
	 */
	@RequestMapping("/course/show/{cid}/{cno}")
	public String showCourse(
            Model model,
            @PathVariable String cid,
            @PathVariable Integer cno
    ){
		Course course = courseService.getAllById(cid,cno);

		if(course == null){
			return sendError(model,"没有找到指定的课程！ cid:"+cid+"  cno:"+cno,logger);
		}

		CourseModel courseModel  = courseService.buildCourseModel(cid,cno,course);
		model.addAttribute("course", course);
		model.addAttribute("courseModel",courseModel);

        courseService.updateCourseStatisticalData(cid,cno);
		return "course/showCourse";	//转到添加页面
	}
	
	
	/**
	 * 增加课程页面
	 * @return
	 */
	@RequestMapping("/course/add")
	public String addCourse(Model model){
		//model.addAttribute("privilegeList", privilegeService.findAll());		
		return "course/addCourse";	//转到添加页面
	}
	
	/**
	 * 保存课程
	 * @return
	 */
	@RequestMapping("/course/save")
	public String save(@ModelAttribute Admin admin){
		return "redirect:/admin/course";	//保存完成后  跳转到管理员列表页面
	}
	
	/**
	 * 删除课程
	 * @param id 需要删除课程Id
	 * 	 * @return	返回到管理员列表页面
	 */
	@RequestMapping("/course/delete/{id}")
	public String delete(@PathVariable int id){
		
		//adminService.delete(id);
		
		return "redirect:/admin/course";	//跳到管理员列表页面
	}

    @RequestMapping("/course-batch/info")
    public @ResponseBody
    BaseResult getCourseBatchInfo(
            @RequestParam String cid,
            @RequestParam Integer cno,
            @RequestParam Integer bid
    ){
        CourseBatch courseBatch = courseBatchService.getById(cid, cno, bid);
        if(courseBatch==null){
            return BaseResult.createFailure("没有找到指定的courseBatch");
        }
        return BaseResult.createSuccess(courseBatch);
    }
}
