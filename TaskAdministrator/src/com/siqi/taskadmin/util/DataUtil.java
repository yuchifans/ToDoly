package com.siqi.taskadmin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DataUtil {

	public static Date createDate(String dueDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		formatter.setLenient(false);
		try {
			// parser convert a string to a date
			Date date = formatter.parse(dueDate);
			formatter.format(date);
			return date;
		} catch (ParseException e) {
			System.out.println("please Enter the correct format of date");
			return null;
		}
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
		if (isDateType(str)) {
			String[] date = str.split("-");
			int month = Integer.parseInt(date[1]);
			int day = Integer.parseInt(date[2]);
			if (month > 12) {
				return false;
			} else if (day > 31) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	public static String dateToString(Date date) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			dateFormat.setLenient(false);
	        //to convert Date to String, use format method of SimpleDateFormat class.
	        String strDate = dateFormat.format(date);
	        return strDate;
	}

	public static boolean isStatus(String str) {
		Pattern pattern = Pattern.compile("^[0-1]{1}$");
		return pattern.matcher(str).matches();
	}
}
