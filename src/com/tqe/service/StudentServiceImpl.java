package com.tqe.service;

import com.tqe.base.enums.ImportType;
import com.tqe.base.vo.PageVO;
import com.tqe.po.*;
import com.tqe.vo.StudentVO;
import com.tqe.vo.TeacherVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl extends BaseService<Student> {


    private static final Log logger = LogFactory.getLog(StudentServiceImpl.class);

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private BatchScoreServiceImpl batchScoreService;

    /**
     * 得到所有的学生列表
     * 不推荐使用
     */
    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }


    /**
     * 根据查询条件得到所有的学生列表
     *
     * @return
     */
    public List<Student> findByPageVO(PageVO pageVO) {
        List<Student> stuList = studentDao.findByPageVO(pageVO);
        if (stuList != null) {
            //不能被发现...
            for (Student stu : stuList) {
                stu.setIdNumber(null);
                stu.setPassword(null);
            }
        }
        return stuList;
    }

    /**
     * 根据学生的id得到学生
     *
     * @param sid
     * @return
     */
    public Student getById(String sid) {
        return studentDao.getById(sid);
    }

    /**
     * 根据学生的id得到学生姓名
     *
     * @param sid
     * @return
     */
    public Student getNameById(String sid) {
        return studentDao.getNameById(sid);
    }

    /**
     * 保存学生信息
     */
    public void save(Student e) {
        studentDao.save(e);
    }


    /**
     * 从excel得到的学生数据
     * 导入到数据库
     *
     * @param list
     * @return
     */
    public ImportResult saveAll(List<Student> list) {
        ImportResult result = null;
        Map<String, Integer> dMap = convertDepListToMap(departmentDao.findAll());
        Map<String, Integer> mMap = convertMajListToMap(majorDao.findAll());
        Map<String, Integer> cMap = convertClaListToMap(classDao.findAll());
        boolean f = false;
        try {
            if (list != null) {
                result = new ImportResult(list.size(), ImportType.STUDENT.getName());
                for (Student s : list) {
                    if (s.getSid() != null) {
                        boolean reload = processStuData(dMap, mMap, cMap, s);
                        if (reload) {        //如果插入学生数据的同时插入了 学院 专业 班级等信息 那么重新加载Map
                            dMap = convertDepListToMap(departmentDao.findAll());
                            mMap = convertMajListToMap(majorDao.findAll());
                            cMap = convertClaListToMap(classDao.findAll());
                        }
                        try {
                            save(s);                    //保存学生到数据库
                            result.addSuccessCnt();
                        } catch (DuplicateKeyException e1) {
                            result.addExitCnt();
                        } catch (Exception e) {
                            logger.info(e.getMessage());
                            result.addFailCnt();
                            result.getFailMegs().add(e.getMessage());
                        }

                    }
                }
                f = true;
            }

        } catch (Exception e) {
            logger.error(e);
        }

        return result;

    }

    /**
     * 处理学生数据 外键关系
     *
     * @param dMap 学院信息
     * @param mMap 专业信息
     * @param cMap 班级信息
     * @param s    学生
     */
    private boolean processStuData(Map<String, Integer> dMap,
                                   Map<String, Integer> mMap, Map<String, Integer> cMap, Student s) {
        String m = s.getMajor();
        boolean reload = false;
        if (m.endsWith(".")) {        //如果专业是以 "." 结束的 那么就把 "." 去掉
            s.setMajor(m.substring(0, m.length() - 1));
        }
        //如果该学生的部门不存在 那么插入该部门
        if (StringUtils.isNoneBlank(s.getDepartment()) && !dMap.containsKey(s.getDepartment())) {
            departmentDao.save(new Department(s.getDepartment()));
            dMap = convertDepListToMap(departmentDao.findAll());    //重新加载
            reload = true;
        }
        s.setDepartmentId(dMap.get(s.getDepartment()));

        //如果学生的专业不存在 那么插入该专业
        if (StringUtils.isNotBlank(s.getMajor()) && !mMap.containsKey(s.getMajor())) {
            Major major = new Major();
            major.setName(s.getMajor());
            major.setDepartmentId(dMap.get(s.getDepartment()));
            majorDao.save(major);
            mMap = convertMajListToMap(majorDao.findAll());    //重新加载
            reload = true;
        }
        s.setMajorId(mMap.get(s.getMajor()));

        //如果学生的班级信息不存在 那么插入该班级
        if (StringUtils.isNotBlank(s.getClazz()) && !cMap.containsKey(s.getClazz())) {
            Clazz clazz = new Clazz();
            clazz.setName(s.getClazz());
            clazz.setMajorId(mMap.get(s.getMajor()));
            clazz.setDepartmentId(dMap.get(s.getDepartment()));
            classDao.save(clazz);
            cMap = convertClaListToMap(classDao.findAll());
            reload = true;
        }
        s.setClassId(cMap.get(s.getClazz()));
        return reload;
    }

    /**
     * 学生登录
     *
     * @param user
     * @return
     */
    public Student login(User user) {
        return studentDao.login(user);
    }


    /**
     * 根据课程号 选出所有选了这门课的学生
     * 如果改教师在改批次已经评价过学生了
     * 那么就设置状态不可评价
     *
     * @param cid
     * @param cno
     * @return
     */
    public List<Student> findAllByCId(String cid, Integer cno, String tid, Integer bid) {
        List<Student> studentList = studentDao.findAllByCId(cid, cno);
        List<String> sidList = evalDao.findAllSidsByCidTid(cid, cno, tid, bid);    //选出对应课程 教师号 和 批次 已经评价过得学生Ids
        for (Student stu : studentList) {        //状态为不可评价
            if (sidList.contains(stu.getSid())) {
                stu.setIsEvaled(true);
            }
        }
        return studentList;
    }


    /**
     */
    public boolean isCoursePermitted(String sid, String cid, Integer cno) {
        return false;
    }

    public void reAnalyseStudentStatistics(String sid) {
        Student stu = studentDao.getById(sid);
        if(stu==null){
            return ;
        }
        if(!DateUtils.isSameDay(stu.getMtime(),new Date())){
            // 一天一次
            batchScoreService.updateStuScore(sid);
            studentDao.updateStuAvgScore(sid);
        }


    }

    public StudentVO getStudentVO(String sid) {
        Student stu = studentDao.getById(sid);
        if(stu == null){
            return null;
        }
        StudentVO vo = new StudentVO(stu);
        List<BatchScore> teacherBatchList = batchScoreService.findAll(sid);
        vo.setBatchScoreList(teacherBatchList);
        return vo;
    }


}
