package com.tqe.service;

import com.tqe.base.enums.DepartmentType;
import com.tqe.po.Department;
import com.tqe.po.SC;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScServiceImpl extends BaseService<SC>{

    Log logger = LogFactory.getLog(ScServiceImpl.class);

	public void saveOne(SC sc){
		scDao.save(sc);
	}

    /**
     * @return 保存学生选课信息
     */
    public int saveAll(List<SC> scList){
        int cnt = 0;
        if(scList!=null){
            for(SC sc: scList){
                try{
                    this.saveOne(sc);
                    cnt++;
                }catch (Exception e){
                    logger.warn(e.getMessage());
                }

            }
        }
        return cnt;
    }


}
