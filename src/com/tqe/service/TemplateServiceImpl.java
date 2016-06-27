package com.tqe.service;

import com.tqe.po.Template;
import com.tqe.po.TemplateItem;
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
        List<TemplateItem> items = templateDao.findItemsByTypeId(typeId);
        //解决 ListHandler 解析时将数字当成整形来处理，需要全部转换成字符串
        // 否则 序列化字符串全部为数字时候，Json解析会报错
        for(TemplateItem item : items){
            List values = item.getValues();
            for(int i=0;i<values.size();i++){
                Object value = values.get(i);
                if(value instanceof  Integer){
                    values.set(i,value.toString());
                }
                if(value instanceof  Double){
                    values.set(i,value.toString());
                }
            }
        }
        return items;
    }

    public void saveItem(TemplateItem item) {
        templateDao.saveItem(item);
    }

    public TemplateItem getItemById(Integer itemId) {
        return templateDao.getItemById(itemId);
    }

    public void updateItem(TemplateItem templateItem) {
        templateDao.updateItem(templateItem);
    }
}
