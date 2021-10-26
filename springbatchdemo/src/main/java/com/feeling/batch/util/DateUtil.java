package com.feeling.batch.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date parseDate(String dateStr) {
		Date date = null;
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		try {
			date = df.parse(dateStr);
		} catch (Exception e) {
			System.out.println("DateUtil.parseDate:" + e);
		}

		return date;
	}

}
