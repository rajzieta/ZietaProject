package com.zieta.tms.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@JsonIgnoreProperties({ "parent" })
public class TasksByClientProjectResponse {

	Long taskInfoId;
	Long projectId;
	Long taskType;
	Long sortKey;
	Long taskParent;
	Long taskStatus;
	String taskManagerName;
	Long taskManager;
	String taskStatusDescription;
//	String projectCode;
//	String taskCode;
	String projectDescription;
	String taskDescription;
	String taskTypeDescription;
	
	@JsonIgnore
	TasksByClientProjectResponse parent;
	List<TasksByClientProjectResponse> children;
	
	public TasksByClientProjectResponse() {
		super();
		this.children = new ArrayList <>();
	}
	
	
	
	public void addChild(TasksByClientProjectResponse child) {
        if (!this.children.contains(child) && child != null)
            this.children.add(child);
    }
 

}
