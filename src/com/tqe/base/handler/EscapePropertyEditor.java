package com.tqe.base.handler;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

/**
 * Created by Maple on 2016/4/22.
 */
public class EscapePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
            text = StringEscapeUtils.escapeHtml4(text);
            text = StringEscapeUtils.escapeEcmaScript(text);
            setValue(text);
    }
}
