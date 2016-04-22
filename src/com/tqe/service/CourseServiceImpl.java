package com.tqe.service;

import com.tqe.base.enums.ImportType;
import com.tqe.base.vo.PageVO;
import com.tqe.model.CourseModel;
import com.tqe.po.*;
import com.tqe.utils.ResultTableUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl extends BaseService<Course> {


    private static final Log logger = LogFactory.getLog(CourseServiceImpl.class);

    @Autowired
    private BatchesServiceImpl batchesService;

    @Autowired
    private EvalServiceImpl evalService;

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    public Course getById(String cid, Integer cno) {
        Course c = courseDao.getById(cid, cno);
        c.setTeacher(teacherDao.getById(c.getTeacherId()));
        return c;
    }

    public Course getAllById(String cid, Integer cno) {
        Course c = courseDao.getAllById(cid, cno);
        c.setTeacher(teacherDao.getById(c.getTeacherId()));
        return c;
    }

    public void save(Course e) {
        courseDao.save(e);
    }

    /**
     * 保存所有课程到数据库
     *
     * @param season 课程季度
     */
    public ImportResult saveAll(List<Course> list, String season) {

        ImportResult result = null;
        Map<String, Integer> dMap = convertDepListToMap(departmentDao.findAll());
        try {
            if (list != null) {
                result = new ImportResult(list.size(), ImportType.STUDENT.getName());
                for (Course c : list) {
                    if (c.getCid() != null && c.getCno() != null) {
                        if(StringUtils.isNotBlank(c.getSeason())){      //确认EXCEL 中的season 和 用户选择要导入的 season 是否一致
                            if(!c.getSeason().equals(season)){
                                result.setMessage("EXCEL中解析到的当前学期为:"+c.getSeason()+"  而您选择的导入学期为："+season);
                                break;
                            }
                        }
                        try {

                            c.setSeason(season);

                            processCouData(dMap, c);
                            save(c);
                            result.addSuccessCnt();
                        } catch (DuplicateKeyException e1){
                            result.addExitCnt();
                        } catch (Exception e) {
                            logger.info(e.getMessage());
                            result.addFailCnt();
                            result.getFailMegs().add(e.getMessage());
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    /**
     * 返回所有学生已经选课的列表，
     * 只返回当前学期可以评教的课程 之前学期的课程不再显示
     * 如果学生已经评价了课程，那么设置课程已评价
     *
     * @param sid     学生Id
     * @param batches 当前可以评教的批次
     */
    public List<Course> findAllBySid(String sid, Batches batches) {
        List<Course> list = courseDao.findAllBySid(sid, batches.getSeason());
        List<String> cids = evalDao.findAllStuTablecids(sid, batches.getId());
        for (Course c : list) {    //如果课程已经评价 那么设置课程已评价
            if (cids.contains(c.getCid())) {
                c.setIsEvaled(true);
            }
        }
        return list;
    }

    /**
     * 返回所有教师可以评价的课程组
     * 如果教师已经评价了课程，那么设置课程已评价
     */
    public List<Course> findCourseGroupByTid(String tid, Integer bid) {
        Batches batch = batchesDao.getById(bid);
        if(batch==null){
            return new ArrayList<>();
        }
        List<Course> list = courseDao.findCourseGroupByTid(tid,batch.getSeason());
        if(list.isEmpty()){
            return list;
        }
        List<String> cids = evalDao.findAllTeaTablecids(tid, bid);  //查询教师已经评教的课程
        for (Course c : list) {
            if (cids.contains(c.getCid() + c.getCno())) {
                c.setIsEvaled(true);
            }
        }
        return list;
    }

    /**
     * 返回所有教师教的所有课程
     */
    public List<Course> findAllByTid(String tid, String season) {

        if(StringUtils.isBlank(season)){
            return courseDao.findAllByTid(tid);
        }
        return courseDao.findAllByTidSeason(tid,season);
    }

    public List<Course> findByCondition(PageVO pageVO) {
        return courseDao.findByCondition(pageVO);
    }

    /**
     * 处理课程数据
     *
     * @param dMap 部门信息
     */
    private void processCouData(Map<String, Integer> dMap, Course cou) {
        cou.setDepartmentId(dMap.get(cou.getDepartment()));
    }

    /**
     * 生成课程的统计数据信息
     */
    public void updateCourseStatisticalData(String cid, Integer cno) {
        List<CourseBatch> courseBatchList = courseBatchDao.getAllByCidAndCno(cid, cno);
        if (courseBatchList.size() == 0) {
            return;
        }
        Course course = courseDao.getById(cid, cno);
        if (course == null) {
            return;
        }
        for (CourseBatch cb : courseBatchList) {
            //设置分数列表
            course.getStuEvalScores().add(cb.getStuEvalAvgScore());
            course.getTeaEvalScores().add(cb.getTeaEvalAvgScore());
            course.getLeaEvalScores().add(cb.getLeaEvalAvgScore());

            //合成课程评教等级统计
            for (int i = 0; i < 4; i++) {
                course.getStuEvalLevelCnts().set(i, course.getStuEvalLevelCnts().get(i) + cb.getStuEvalLevelCnts().get(i));
                course.getTeaEvalLevelCnts().set(i, course.getTeaEvalLevelCnts().get(i) + cb.getTeaEvalLevelCnts().get(i));
                course.getLeaEvalLevelCnts().set(i, course.getLeaEvalLevelCnts().get(i) + cb.getLeaEvalLevelCnts().get(i));
            }

        }
        course.setStuEvalAvgScore(ResultTableUtils.avgList(course.getStuEvalScores()));
        course.setTeaEvalAvgScore(ResultTableUtils.avgList(course.getTeaEvalScores()));
        course.setLeaEvalAvgScore(ResultTableUtils.avgList(course.getLeaEvalScores()));

        courseDao.updateStatisticalData(course);

    }

    public CourseModel buildCourseModel(String cid,  Integer cno, Course course) {

        CourseModel courseModel = new CourseModel(course);
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
        return courseModel;
    }




}
