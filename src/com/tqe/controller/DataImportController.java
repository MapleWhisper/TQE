package com.tqe.controller;

import com.tqe.excelreader.ExcelReader;
import com.tqe.excelreader.ExcelUtils;
import com.tqe.po.*;
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
import java.io.IOException;
import java.util.List;

/**
 * 数据导入控制器
 */
@Controller
@RequestMapping("/admin")
public class DataImportController extends BaseController {

    private static final String DATA_IMPORT = "dataImport/dataImport";

    private static final String IMPORT_RESULT = "dataImport/import-result";

    private static final Log logger = LogFactory.getLog(DataImportController.class);

    @Resource(name = "teacherExcelReader")
    private ExcelReader<Teacher> teacherReader;

    @Resource(name = "courseExcelReader")
    private ExcelReader<Course> courseReader;

    @Resource(name = "studentExcelReader")
    private ExcelReader<Student> studentReader;

    @Resource(name = "scExcelReader")
    private ExcelReader<SC> scReader;


    /**
     * 显示数据导入首页
     */
    @RequestMapping("/dataImport")
    public String dataImport() {
        return DATA_IMPORT;
    }

    /**
     * 导入教师信息
     */
    @RequestMapping("/dataImport/teacher")
    public String dataImportTeacher(
            HttpServletRequest request,
            @RequestParam("teacherFile") CommonsMultipartFile teacherFile,
            Model model
    ) {

        try {
            if (!checkFileName(teacherFile, model)) {
                return IMPORT_RESULT;
            }
            String fileDir = storeFile(request, teacherFile);
            if(!ExcelUtils.checkFileType(fileDir)){
                model.addAttribute("error","文件的格式必须为2003版本的EXCEL文件");
                return IMPORT_RESULT;
            }
            List<Teacher> teacherList = teacherReader.getAll(fileDir, true);

            ImportResult result = teacherService.saveAll(teacherList);
            model.addAttribute("importResult",result);

        }  catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        }
        return IMPORT_RESULT;
    }

    /**
     * 导入学生信息
     */
    @RequestMapping("/dataImport/student")
    public String dataImportStudent(
            HttpServletRequest request,
            @RequestParam("studentFile") CommonsMultipartFile studentFile,
            Model model
    ) {
        try {
            if (!checkFileName(studentFile, model)) {
                return IMPORT_RESULT;
            }
            String fileDir = storeFile(request, studentFile);
            List<Student> studentList = studentReader.getAll(fileDir, true);
            ImportResult result = studentService.saveAll(studentList);
            model.addAttribute("importResult",result);

        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        }
        return IMPORT_RESULT;

    }

    /**
     * 导入课程信息
     */
    @RequestMapping("/dataImport/course")
    public String dataImportCourse(
            HttpServletRequest request,
            @RequestParam("courseFile") CommonsMultipartFile courseFile,
            @RequestParam("season") String season,
            Model model
    ) {
        if (StringUtils.isBlank(season)) {    //导入课程必须选择课程的学期
            model.addAttribute("error", "学期不能为空！");
        }else{
            try {
                if (checkFileName(courseFile, model)) {
                    String fileDir = storeFile(request, courseFile);
                    if(!ExcelUtils.checkFileType(fileDir)){
                        model.addAttribute("error","文件的格式必须为2003版本的EXCEL文件");
                        return IMPORT_RESULT;
                    }
                    List<Course> courseList = courseReader.getAll(fileDir, false);
                    ImportResult result = courseService.saveAll(courseList, season);
                    model.addAttribute("importResult",result);
                }
            } catch (Exception e) {
                model.addAttribute("error", e.getMessage());
                logger.error(e);
            }
        }
        return IMPORT_RESULT;
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
                return IMPORT_RESULT;
            }
            String fileDir = storeFile(request, scFile);
            List<SC> scList = scReader.getAll(fileDir, false);
            scService.saveAll(scList);

        } catch (IOException e) {
            model.addAttribute("error", e.getMessage());
            logger.error(e);
        }
        return IMPORT_RESULT;
    }


    /**
     * 检查文件的后缀是否是Excel
     */
    private boolean checkFileName(CommonsMultipartFile file, Model model) {
        String fileName = file.getOriginalFilename();
        if (!fileName.trim().endsWith(".xls")) {
            model.addAttribute("error", "文件必须是.xls结尾的EXCEL文件");
            return false;
        }

        return true;
    }

    /**
     * 保存上传的文件到临时的目录中
     * 如果目录已经存在同名文件 那么就覆盖
     * @return 返回保存文件的绝对路径
     */
    private String storeFile(
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
