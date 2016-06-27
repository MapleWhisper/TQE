package com.tqe.controller;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.tqe.base.BaseResult;
import com.tqe.po.Privilege;
import com.tqe.po.Template;
import com.tqe.po.TemplateItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 权限管理控制器
 * @author 于广路
 */
@Controller()
@RequestMapping("/admin")
public class TemplateController extends BaseController{

    private static final Log logger = LogFactory.getLog(TeacherController.class);

    @RequestMapping("/template")
    public String template(
            @RequestParam(required = false) String type,
            Model model

    ){
        List<Template> templateList = templateService.findAll();
        model.addAttribute("templateList",templateList);
        if(StringUtils.isNotBlank(type)){
            Template template = templateService.getAllByType(type);
            if(template!=null){
                model.addAttribute("template",template);
            }else{
                return sendError(model,"未知的模板类型type:"+type,logger);
            }
        }
        return "template/template";
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

    @RequestMapping("/template/save")
    @ResponseBody
    public BaseResult saveTemplate(
            @RequestParam String type,
            @RequestParam List<String> values
    ){

        Template template = templateService.getByType(type);
        if(template==null){
            return BaseResult.createFailure("未知的模板类型");
        }
        if(values==null || values.isEmpty()){
            return BaseResult.createFailure("模板数据不能为空");
        }
        if(values.size()!=template.getColumns().size()){
            return BaseResult.createFailure("模板数据的记录数和模板列数不一致，请输入全部数据");
        }
        TemplateItem item = new TemplateItem();

        item.setTypeId(template.getId());
        item.setValues(values);
        templateService.saveItem(item);
        return BaseResult.createSuccess("保存成功");

    }

    @RequestMapping("/template/update")
    @ResponseBody
    public BaseResult updateTemplate(
            @RequestParam Integer itemId,
            @RequestParam List<String> values
    ){

        TemplateItem templateItem = templateService.getItemById(itemId);
        if(templateItem==null){
            return BaseResult.createFailure("未知的记录");
        }
        if(values==null || values.isEmpty()){
            return BaseResult.createFailure("模板数据不能为空");
        }

        templateItem.setValues(values);
        templateService.updateItem(templateItem);
        return BaseResult.createSuccess("修改成功");

    }

}
