package com.tqe.utils;

import java.util.Calendar;
import java.util.Properties;

public class SystemUtils extends Properties{
	
	/**
	 * 得到当前的学期
	 * 如果当前月份大于2月，小于8月，那么默认为春季学期
	 * 如果月份大于8月，小于第二年2月
	 */
	public static String getSeason()  {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		String season = "";
		if(month>=2 && month <8){
			season = year+"春";
		}else{
			season = year+"秋";
		}
		return season;
	}
	public static String getSeasonT()  {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		String seasont = "";
		if(month>=2 && month <8){
			seasont = year+"s";
		}else{
			seasont = year+"a";
		}
		return seasont;
	}
}
