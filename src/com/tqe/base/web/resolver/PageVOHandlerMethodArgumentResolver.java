package com.tqe.base.web.resolver;

import com.tqe.base.enums.EvalTableType;
import com.tqe.base.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * 把前端传来的参数转换成PageVO对象
 * Created by Maple on 2015/12/2.
 */
public class PageVOHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static Map<String,Map<String,String>> valueConvertMap;
    static {
        valueConvertMap = new HashMap<String, Map<String, String>>();
        addTypeConvertMap();
    }

    private static void addTypeConvertMap() {
        Map<String,String> map = new HashMap<String, String>();
        for(EvalTableType type : EvalTableType.values()){
            map.put(type.toString().toLowerCase(),type.getName());
        }
        valueConvertMap.put("type",map);
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == PageVO.class;
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) throws Exception {
        PageVO pageVO = new PageVO();
        Map<String,String[]> paramMap = nativeWebRequest.getParameterMap();
        for(String key : paramMap.keySet()){
            String value[] = paramMap.get(key);
            if(value!=null && value.length>0){
                String v = value[0];
                if(StringUtils.isNotBlank(v)){

                    pageVO.getFilters().put(key,valueConverter(key,v).trim());

                }
            }
        }
        return pageVO;
    }

    /**
     * 对存入对象的值 进行转换
     */
    private String valueConverter(String key, String value) {
        if(valueConvertMap.containsKey(key)){
            Map<String,String> map = valueConvertMap.get(key);
            if(map.containsKey(value.toLowerCase())){
                return map.get(value.toLowerCase());
            }
        }
        return value;
    }
}
