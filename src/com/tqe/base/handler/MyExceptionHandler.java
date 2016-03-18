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
        messageConvertMap.put("OLE2","�Բ������ϴ���EXCEL��ʽ����ȷ����ת��Ϊ��ȷ��ʽ�����ԣ�");
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {

        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("msg", messageConvert(ex.getMessage()));
        logger.error("�����ˣ�",ex);
        return modelAndView;
    }


    /**
     * �Բ���Ĵ�����Ϣ����ת��˵��
     */
    public String messageConvert(String errorMsg){

        if(StringUtils.isBlank(errorMsg)){
            return "��Ǹ��ϵͳ������δ֪�Ĵ���";
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