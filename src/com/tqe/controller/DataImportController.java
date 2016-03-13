package com.tqe.controller;

import com.tqe.excelreader.ExcelReader;
import com.tqe.po.Course;
import com.tqe.po.SC;
import com.tqe.po.Student;
import com.tqe.po.Teacher;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 用来控制 将excel文件导入数据库的控制器
 *
 * @author 广路
 */
@Controller
@RequestMapping("/admin")
public class DataImportController extends BaseController {

    Log logger = LogFactory.getLog(DataImportController.class);

    @Resource(name = "teacherExcelReader")
    private ExcelReader<Teacher> teacherReader;

    @Resource(name = "courseExcelReader")
    private ExcelReader<Course> courseReader;

    @Resource(name = "studentExcelReader")
    private ExcelReader<Student> studentReader;

    @Resource(name = "scExcelReader")
    private ExcelReader<SC> scReader;

    @RequestMapping("/dataImport")
    public String dataImport() {
        return "dataImport/dataImport";
    }

    /**
     * 教师信息导入
     */
    @RequestMapping("/dataImport/teacher")
    public String dataImportTeacher(
            HttpServletRequest request,
            @RequestParam("teacherFile") CommonsMultipartFile teacherFile,
            Model model
    ) {

        try {
            if (!checkFileName(teacherFile, model)) {
                return "dataImport/dataImport";
            }
            String fileDir = storeFileAndReturnFilePath(request, teacherFile);
            List<Teacher> teacherList = teacherReader.getAll(fileDir, true);
            teacherService.saveAll(teacherList);

        } catch (FileNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        }
        return "dataImport/dataImport";
    }

    /**
     * 学生信息导入
     */
    @RequestMapping("/dataImport/student")
    public String dataImportStudent(
            HttpServletRequest request,
            @RequestParam("teacherFile") CommonsMultipartFile studentFile,
            Model model
    ) {
        try {
            if (!checkFileName(studentFile, model)) {
                return "dataImport/dataImport";
            }
            String fileDir = storeFileAndReturnFilePath(request, studentFile);
            List<Student> studentList = studentReader.getAll(fileDir, true);
            studentService.saveAll(studentList);

        } catch (FileNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        }
        return "dataImport/dataImport";

    }

    /**
     * 课程班信息导入
     */
    @RequestMapping("/dataImport/course")
    public String dataImportCourse(
            HttpServletRequest request,
            @RequestParam("courseFile") CommonsMultipartFile courseFile,
            @RequestParam("season") String season,
            Model model
    ) {
        if (StringUtils.isBlank(season)) {    //如果season为空，那么不能继续
            model.addAttribute("error", "season不能为空！");
        }else{
            try {
                if (checkFileName(courseFile, model)) {
                    String fileDir = storeFileAndReturnFilePath(request, courseFile);
                    List<Course> courseList = courseReader.getAll(fileDir, false);
                    courseService.saveAll(courseList,season);
                }
            } catch (FileNotFoundException e) {
                model.addAttribute("error", e.getMessage());
                logger.error(e);
            } catch (IOException e) {
                model.addAttribute("error", e.getMessage());
                logger.error(e);
            }
        }
        return "dataImport/dataImport";
    }

    /**
     * 学生选课信息导入
     */
    @RequestMapping("/dataImport/sc")
    public String dataImportSc(
            HttpServletRequest request,
            @RequestParam("scFile") CommonsMultipartFile scFile,
            Model model
    ) {
        try {
            if (!checkFileName(scFile, model)) {
                return "dataImport/dataImport";
            }
            String fileDir = storeFileAndReturnFilePath(request, scFile);
            List<SC> scList = scReader.getAll(fileDir, false);
            scService.saveAll(scList);

        } catch (FileNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        }
        return "dataImport/dataImport";
    }

    /**
     * 检查文件名是否合法
     * 合法返回true 否则返回false
     */
    private boolean checkFileName(CommonsMultipartFile file, Model model) {
        String fileName = file.getOriginalFilename();
        if (!fileName.trim().endsWith(".xls")) {  //如果文件不是excel  那么报错
            model.addAttribute("error", "文件必须是要以.xls结尾的Excel文件！");
            return false;
        }
        return true;
    }

    private String storeFileAndReturnFilePath(
            HttpServletRequest request,
            CommonsMultipartFile uploadFile) throws IOException {

        String baseDir = request.getServletContext().getRealPath("/") + "/upload/";
        String fileDir = baseDir + uploadFile.getOriginalFilename();
        File file = new File(fileDir);
        if (file.exists()) {
            file.delete();
        }
        FileUtils.copyInputStreamToFile(uploadFile.getInputStream(), file);
        return fileDir;
    }


}
