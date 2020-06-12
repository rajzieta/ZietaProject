package com.zietaproj.zieta.response;

import java.util.Date;

import com.zietaproj.zieta.model.TaskMaster;

import lombok.Data;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class TasktypesByClientResponse {

	long id;
	long clientId;
	String type_name;
	String created_by;
	String modified_by;
	Date created_date;
	Date modified_date;
	boolean is_delete;
}
