package com.zieta.tms.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name = "task_info")
@Data
public class TaskInfo extends BaseEntity implements Serializable {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id")
	    private Long taskInfoId;

	    @Column(name = "client_id")
	    private Long clientId;
	
	    @Column(name = "project_id")
	    private Long projectId;
	    
	    @Column(name = "task_name")
	    private String taskDescription;
	    
	    @Column(name ="task_code")
	    private String taskCode;
	    
	    @Column(name = "task_type")
	    private Long taskType;
	    
	    @Column(name = "task_parent")
	    private Long taskParent;
	    
	    @Column(name = "task_manager")
	    private Long taskManager;
	    
	    @Column(name = "task_status")
	    private Long taskStatus;
	    
	    @Column(name = "sortkey")
	    private Long sortKey;
	
}
