package com.tqe.utils;

import java.util.Calendar;
import java.util.Properties;

public class SystemUtils extends Properties{
	
	/**
	 * �õ���ǰ��ѧ��
	 * �����ǰ�·ݴ���2�£�С��8�£���ôĬ��Ϊ����ѧ��
	 * ����·ݴ���8�£�С�ڵڶ���2��
	 */
	public static String getSeason()  {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH)+1;
		String season = "";
		if(month>=2 && month <8){
			season = year+"��";
		}else{
			season = year+"��";
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
