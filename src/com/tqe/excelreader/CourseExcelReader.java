package com.tqe.excelreader;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.tqe.dao.TeacherDao;
import com.tqe.po.Course;
import com.tqe.po.Teacher;

@Component
public class CourseExcelReader extends ExcelReader<Course> {
	public static void main(String[] args) throws FileNotFoundException {
		List<Course> list = new CourseExcelReader().getAll("d:/课程班信息：课程-老师对应关系.xls");
		//System.out.println(list.size());
		
		for(Course c: list){
			System.out.println(c.getName()+"\t"+c.getCid()+"\t"+c.getCno()+"\t");
		}
	}
	
	
	
	/**
	 * 检查文件是否是教师文件
	 */
	@Override
	public boolean checkFile(String excelDir) {
		List<String> list = ExcelUtils.getRow(excelDir, 0, 2);
		
		if(! (list.contains("课程号")&&list.contains("课程名称")) ){
            throw new IllegalArgumentException("课程文件中必须包含 课程号 和 课程名称！ 请检查您要导入的文件是否正确");
        }
        return  true;

	}

    @Override
    protected void postProcessList(List<Course> list,String excelDir) {


        super.postProcessList(list,excelDir);

        /**
         * 从Excel表头中获得当前的 学期 season 然后注入到 list中
         */
        List<String> headRowList = ExcelUtils.getRow(excelDir, 0, 0);
        String season = "";
        for(String headRow : headRowList){
            int i = 0;
            for(i = 2012 ; i<= 3000;i++){
                if(headRow.contains(i+"")){
                    season+=(i+"");
                    break;
                }
            }
            if(i==3000){    //没找到
                continue;
            }
            if(headRow.contains("春")){
                season+="春";
            }else if(headRow.contains("球")){
                season+="秋";
            }
            if(season.length()==5){
                break;
            }

        }
        if(season.length()==5){
            for(Course c: list){
                c.setSeason(season);;
            }
        }
    }
}
