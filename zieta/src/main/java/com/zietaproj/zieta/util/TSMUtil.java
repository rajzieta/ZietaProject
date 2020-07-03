package com.zietaproj.zieta.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

import com.zietaproj.zieta.model.UserInfo;

public class TSMUtil {
	
	private final static String SPACE = " ";
	
	public static String getFullName(UserInfo userInfo) {

		StringBuilder userName = new StringBuilder(userInfo.getUser_fname());
		if (!StringUtils.isEmpty(userInfo.getUser_mname())) {
			userName.append(SPACE).append(userInfo.getUser_mname());
		}
		userName.append(SPACE).append(userInfo.getUser_lname());
		return userName.toString();
	}
	
	
	public static String getFormattedDateAsString(Date date) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String formattedDate = null;

			formattedDate = simpleDateFormat.format(date);
		return formattedDate;
	}
	
	public static Date getFormattedDate(Date date) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Date formattedDate = null;
		try {
			formattedDate = simpleDateFormat.parse(simpleDateFormat.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formattedDate;
	}
	
	public static LocalDate convertToLocalDateViaMilisecond( Date dateToConvert) {
	    
		if(dateToConvert != null) {
		return Instant.ofEpochMilli(dateToConvert.getTime())
	      .atZone(ZoneId.systemDefault())
	      .toLocalDate();
		}else {
			return null;
		}
	}

}
