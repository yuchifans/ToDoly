package com.siqi.taskadmin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * This class contains several methods which are used to verify whether the
 * input data is in correct format or return data in certain format.
 * 
 * @author Siqi Qian
 * @version 2018.10.04
 */

public class DataUtil {

	/**
	 * Verify the certain format of the String. If the format is "dd-MM-yyyy",
	 * return an object of Date by parsing the String, throw an parseException and
	 * print an exception messege otherwise.
	 * 
	 * @param dateStr The string to be verified
	 * @return an object of Date  
	 */
	public static Date createDate(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		formatter.setLenient(false);
		try {
			// try to parse the String, if it is in correct format, return an object of Date
			// corresponding to the string
			Date date = formatter.parse(dateStr);
			return date;
		} catch (ParseException e) {
			// if the string is not in correct format, print an error messege.
			System.out.println("please Enter the correct format of date");
			return null;
		}
	}

	/**
	 * Check whether the input string matches pattern of decimal number.
	 * 
	 * @param str The string to be verified
	 * @return true if the input is pattern of number, false otherwise
	 */

	public static boolean isInteger(String str) {
			Pattern pattern = Pattern.compile("^[\\d]*$");
			boolean isInteger=false;
			if(pattern.matcher(str.trim()).matches()) {
				try {
					Integer.parseInt(str.trim());
					isInteger=true;
				}catch(NumberFormatException e){
					System.out.println("The number you typed in is too large");
				}
			}
			return isInteger;
	}

	/**
	 * Convert an object of Date to a string with format of "dd-MM-yyyy".
	 * 
	 * @param date an object of Date to be transformed
	 * @return a string with format of "dd-MM-yyyy"
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		// to convert Date to String, use format method of SimpleDateFormat class.
		String strDate = dateFormat.format(date);
		return strDate;
	}

	/**
	 * Check whether the input string is "0" or "1".
	 * 
	 * @param str the string to be verified
	 * @return true if the input is "0" or "1", false otherwise
	 */
	public static boolean isStatus(String str) {
		Pattern pattern = Pattern.compile("^[0-1]{1}$");
		return pattern.matcher(str.trim()).matches();
	}
}
