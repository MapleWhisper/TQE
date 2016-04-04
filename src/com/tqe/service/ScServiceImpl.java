package com.tqe.service;

import com.tqe.base.enums.DepartmentType;
import com.tqe.base.enums.ImportType;
import com.tqe.po.Department;
import com.tqe.po.ImportResult;
import com.tqe.po.SC;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScServiceImpl extends BaseService<SC>{

    private static  final Log logger = LogFactory.getLog(ScServiceImpl.class);

	public void saveOne(SC sc){
		scDao.save(sc);
	}

    /**
     * @return 保存学生选课信息
     */
    public ImportResult saveAll(List<SC> scList){
        ImportResult result = null;
        if(scList!=null){
            result = new ImportResult(scList.size(), ImportType.SC.getName());
            for(SC sc: scList){
                try{
                    try {
                        this.saveOne(sc);					//保存教师到数据库
                        result.addSuccessCnt();
                    } catch (DuplicateKeyException e1){
                        result.addExitCnt();
                    } catch (Exception e) {
                        logger.info(e.getMessage());
                        result.addFailCnt();
                        result.getFailMegs().add(e.getMessage());
                    }

                }catch (Exception e){
                    logger.warn(e.getMessage());
                }

            }
        }
        return result;
    }


}
