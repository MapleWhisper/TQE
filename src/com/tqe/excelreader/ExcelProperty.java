package com.tqe.excelreader;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

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
