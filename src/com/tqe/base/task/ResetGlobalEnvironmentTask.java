package com.tqe.base.task;

import com.tqe.utils.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Calendar;

/**
 * Created by Maple on 2016/3/19.
 * 定时任务
 */
@Component
@Lazy(value=false)
public class ResetGlobalEnvironmentTask {

    Log logger = LogFactory.getLog(ResetGlobalEnvironmentTask.class);

    /**
     * 1分钟一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void resetEnvironment() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        ServletContext applicationContext = webApplicationContext.getServletContext();

        resetCurYear(applicationContext);

    }

    /**
     * 重新设置当前年份
     */
    private void resetCurYear(ServletContext applicationContext){
        int year = Calendar.getInstance().get(Calendar.YEAR);

        applicationContext.setAttribute("curYear",year);
        applicationContext.setAttribute("curSeason", SystemUtils.getSeason());

        //logger.info("reset curYear :"+year);
    }
}
