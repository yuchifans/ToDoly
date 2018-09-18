package com.siqi.taskadmin;

import java.util.regex.Pattern;

public class DataUtil {

	public DataUtil() {
		
	}
	
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

}
