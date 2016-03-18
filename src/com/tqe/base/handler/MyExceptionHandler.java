package com.tqe.base.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maple on 2016/3/12.
 */
@Component
public class MyExceptionHandler implements HandlerExceptionResolver {

    Log logger = LogFactory.getLog(MyExceptionHandler.class);

    private  static  Map<String,String> messageConvertMap = new HashMap<String,String>();
    static {
        messageConvertMap.put("OLE2","对不起，您上传的EXCEL格式不正确，请转换为正确格式后再试！");
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg", messageConvert(ex.getMessage()));
        logger.error("出错了！",ex);
        return modelAndView;
    }


    /**
     * 对捕获的错误消息进行转换说明
     */
    public String messageConvert(String errorMsg){

        if(StringUtils.isBlank(errorMsg)){
            return "抱歉，系统出现了未知的错误";
        }

        for(String keyValue:messageConvertMap.keySet()){
            if(errorMsg.contains(keyValue)){
                errorMsg = messageConvertMap.get(keyValue)+"\n"+errorMsg;
                break;
            }
        }
        return errorMsg;


    }
}