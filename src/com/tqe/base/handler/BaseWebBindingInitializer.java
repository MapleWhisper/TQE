package com.tqe.base.handler;

/**
 * Created by Maple on 2016/4/22.
 */

import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

@Component
public class BaseWebBindingInitializer implements WebBindingInitializer {


    @Override
    public void initBinder(WebDataBinder binder, WebRequest request) {

        binder.registerCustomEditor(String.class, new EscapePropertyEditor());
    }

}