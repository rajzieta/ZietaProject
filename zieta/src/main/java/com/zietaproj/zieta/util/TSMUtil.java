package com.zietaproj.zieta.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.zietaproj.zieta.model.UserInfo;
import com.zietaproj.zieta.response.TasksByClientProjectResponse;

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
	
	
	 public static List<TasksByClientProjectResponse> createTree(List<TasksByClientProjectResponse> nodes) {

	        Map<Long, TasksByClientProjectResponse> mapTmp = new HashMap<>();
	        List<TasksByClientProjectResponse> treeTask = new ArrayList<>();
	        
	        //Save all nodes to a map
	        for (TasksByClientProjectResponse current : nodes) {
	            mapTmp.put(current.getTaskInfoId(), current);
	        }

	        //loop and assign parent/child relationships
	        for (TasksByClientProjectResponse current : nodes) {
	            long parentId = current.getTaskParent();

	            if (parentId != 0) {
	            	TasksByClientProjectResponse parent = mapTmp.get(parentId);
	                if (parent != null) {
	                    current.setParent(parent);
	                    parent.addChild(current);
	                    mapTmp.put(parentId, parent);
	                    mapTmp.put(current.getTaskInfoId(), current);
	                }
	            }

	        }

	    
	        //get the root
	        for (TasksByClientProjectResponse node : mapTmp.values()) {
	            if(node.getParent() == null) {
	            	treeTask.add(node);
	            }
	        }
	        
	        return treeTask;
	    } 

}
