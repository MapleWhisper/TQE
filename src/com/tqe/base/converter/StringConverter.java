package com.tqe.base.converter;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Maple on 2016/4/22.
 */
@Component
public class StringConverter implements Converter<String,String> {

    @Override
    public String convert(String text) {
        if(StringUtils.isNotBlank(text)){
            if(text.contains("<") && text.contains(">")){
                text = StringEscapeUtils.escapeHtml4(text);
                text = StringEscapeUtils.escapeEcmaScript(text);
            }
        }
        return text;
    }
}
