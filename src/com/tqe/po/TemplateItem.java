package com.tqe.po;

import java.util.List;

/**
 * Created by Maple on 2016/6/4.
 */
public class TemplateItem {

    private Integer id;

    private Integer typeId;
    private List<String> values;

    private Template template;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
