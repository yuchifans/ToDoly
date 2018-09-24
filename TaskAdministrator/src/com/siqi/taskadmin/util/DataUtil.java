package com.siqi.taskadmin.util;

import java.util.regex.Pattern;

public class DataUtil {

	public DataUtil() {
		
	}
	
	public static boolean isDateType(String str) {
		Pattern pattern = Pattern.compile("^[\\d]{4}-[\\d]{2}-[\\d]{2}$");
		return pattern.matcher(str).matches();
	}
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[\\d]*$");
		return pattern.matcher(str).matches();
	}


	
	public static boolean isDate(String str) {
		if(isDateType(str)) {
			String[] date=str.split("-");
			int month=Integer.parseInt(date[1]);
			int day=Integer.parseInt(date[2]);
			if(month>12) {
				return false;
			}else if(day>31) {
				return false;
			}
			return true;
		}else {
			return false;
		}	
	}
	
	public static boolean isStatus(String str) {
		Pattern pattern = Pattern.compile("^[0-1]{1}$");
		return pattern.matcher(str).matches();
	}
}
