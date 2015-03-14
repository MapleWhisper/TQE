package com.tqe.excelreader;

import java.io.IOException;
import java.util.Properties;

import javax.ws.rs.GET;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public  class ExcelProperty {
	public static Properties excelpro ;
	static{
		excelpro = new Properties();
		try {
			excelpro.load(ExcelProperty.class.getClassLoader().getResourceAsStream("excelMap.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return excelpro.getProperty(key);
	
	}
}
