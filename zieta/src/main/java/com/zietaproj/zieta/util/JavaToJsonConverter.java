package com.zietaproj.zieta.util;

import java.util.Date;
import com.google.gson.Gson;

import com.zietaproj.zieta.model.ActivityMaster;

public class JavaToJsonConverter {

	
public static void main(String args[]) {
		
		ActivityMaster activityMaster = new ActivityMaster();
		activityMaster.setActivity_code("activitycode");
		activityMaster.setActivity_desc("activity desc");
		activityMaster.setCreated_by("xyz");
		activityMaster.setCreated_date(new Date(System.currentTimeMillis()));
		activityMaster.setIS_DELETE(false);
		activityMaster.setModified_by("mgr");
		activityMaster.setModified_date(new Date(System.currentTimeMillis()));
		activityMaster.setProject_id(1234L);
		
		Gson gson = new Gson();    
	    String json = gson.toJson(activityMaster);
	    
	    System.out.println(json);

		
		
	}

}
