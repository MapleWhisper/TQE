package com.tqe.service;

import com.tqe.po.Clazz;
import com.tqe.po.Template;
import com.tqe.po.TemplateItem;
import org.springframework.expression.common.TemplateAwareExpressionParser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateServiceImpl extends BaseService<Template>{

    public List<Template> findAll(){
        return templateDao.findAll();
    }

    public Template getById(Integer id){
        return templateDao.getById(id);
    }

    public Template getByType(String type){
        return templateDao.getByType(type);
    }

    public Template getAllByType(String type){
        Template template = getByType(type);
        if(template==null){
            return null;
        }
        List<TemplateItem> items = findItemsByTypeId(template.getId());
        template.setItems(items);
        return template;
    }
    public Template getAllByTemplateId(Integer templateId){
        Template template = getById(templateId);
        if(template==null){
            return null;
        }
        return this.getAllByType(template.getType());
    }

    public List<TemplateItem> findItemsByTypeId(Integer typeId) {
        return templateDao.findItemsByTypeId(typeId);
    }
}
