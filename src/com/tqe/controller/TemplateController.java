package com.tqe.controller;

import com.tqe.base.BaseResult;
import com.tqe.po.Privilege;
import com.tqe.po.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 权限管理控制器
 * @author 于广路
 */
@Controller()
@RequestMapping("/admin")
public class TemplateController extends BaseController{
	
	@RequestMapping("/template")
    public String template(){

        return "template/template";
    }

    @RequestMapping("/template/show")
    public String showTemplate(){
        return "template/show-template";
    }

    @RequestMapping("/template/getInfo")
    @ResponseBody()
    public BaseResult getTemplateInfo(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String type
    ){
        if( id==null && StringUtils.isBlank(type)){
           return BaseResult.createFailure("模板Id和模板类型Type必须要传递一个参数");
        }
        Template template = null;
        if(id!=null && id>0){
            template = templateService.getAllByTemplateId(id);
        }
        if(StringUtils.isNotBlank(type)){
            template = templateService.getAllByType(type);
        }
        if(template==null){
            return BaseResult.createFailure("没有知道指定的模板记录！");
        }else{
            return BaseResult.createSuccess(template);
        }
    }

}
