package com.tsing.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}

}
