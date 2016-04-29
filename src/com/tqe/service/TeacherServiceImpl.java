package com.tqe.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tqe.base.enums.ImportType;
import com.tqe.base.vo.PageVO;
import com.tqe.po.BatchScore;
import com.tqe.po.Department;
import com.tqe.po.ImportResult;
import com.tqe.vo.TeacherVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.tqe.po.Teacher;
import com.tqe.po.User;

@Service
public class TeacherServiceImpl extends BaseService<Teacher>{

    private static final Log logger = LogFactory.getLog(TeacherServiceImpl.class);

    @Autowired
    private BatchScoreServiceImpl batchScoreService;

    @Autowired
    private CourseBatchServiceImpl courseBatchService;
	
	public Teacher getById(String id) {
		return  teacherDao.getById(id);
	}
	
	@Override
	public void save(Teacher e) {
		teacherDao.save(e);
	}
	
	public ImportResult saveAll(List<Teacher> list){
		boolean f = false;
        ImportResult result = null;
		Map<String,Integer> dMap = convertDepListToMap(departmentDao.findAll());
		try {
			if(list!=null){
                result  = new ImportResult(list.size(), ImportType.TEACHER.getName());
				for(Teacher t:list){
					if(t.getId()!=null){ 
						boolean reload = processTeaData(dMap, t);   //教师数据预处理
						if(reload){	//如果插入教师过程中 添加了学院信息 那么重新加载Map
							dMap = convertDepListToMap(departmentDao.findAll());
						}
                        try {
                            save(t);					//保存教师到数据库
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
				f= true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	@Override
	public List<Teacher> findAll() {
		
		return teacherDao.findAll();
	}
	
	public Teacher login(User user) {
		return teacherDao.login(user);
	}



	public List<Teacher> findByPageVO(PageVO pageVO) {
		List<Teacher> teacherList = teacherDao.findByPageVO(pageVO);
		for(Teacher t : teacherList){
			t.setIdNumber(null);
		}
		return teacherList;
	}
	
	/**
	 * 处理教师数据
	 *  如果教师对应的学院不存在 那么就插入该学院
	 * @param dMap 学院信息
	 */
	private boolean processTeaData(Map<String, Integer> dMap, Teacher tea) {
		boolean reload = false;
		if(StringUtils.isNoneBlank(tea.getDepartment()) && !dMap.containsKey(tea.getDepartment()) ) {
			departmentDao.save(new Department(tea.getDepartment()));
			dMap = convertDepListToMap(departmentDao.findAll());	//重新加载
			reload = true;

		}
		tea.setDepartmentId(dMap.get(tea.getDepartment()));
		return reload;
	}

    public void reAnalyseStatistics(String tid) {
        Teacher tea = teacherDao.getById(tid);
        if(tea==null){
            return ;
        }
        if(!DateUtils.isSameDay(tea.getMtime(),new Date())){
            batchScoreService.updateTeaScore(tid);      //更新教师每学期得分
            teacherDao.updateStatisticsData(tid);       //更新教师总得分

        }


    }

    public TeacherVO getTeacherVO(String tid) {
        Teacher tea  = teacherDao.getById(tid);
        if(tea==null){
            throw null;
        }
        TeacherVO vo = new TeacherVO(tea);
        List<BatchScore> teacherBatchList = batchScoreService.findAll(tid);
        vo.setBatchScoreList(teacherBatchList);
        return vo;
    }
}
