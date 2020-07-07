package com.zietaproj.zieta.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;



@Entity
@Table(name = "TS_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class TSInfo extends BaseEntity implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Long tsInfoId;

	@Column(name="client_id")
    private Long clientId;

	@Column(name="time_type")
    private Long timeType;
    
	@Column(name="project_id")
    private Long projectId;
    
	@Column(name="activity_id")
    private Long activityId;
    
	@Column(name="user_id")
    private Long userId;
    
	@Column(name="task_id")
    private Long taskId;
    
	@Column(name="status_id")
    private Long statusId;
    
	@Column(name="planned_activity")
    private boolean plannedActivity;
    
	@Column(name="ts_date")
    private Date tsDate;
    
	@Column(name="TS_START_TIME")
    private  float tsStartTime;
    
	@Column(name="TS_END_TIME")
    private  float tsEndTime;
    
	@Column(name="TS_TOTAL_TIME")
    private  float tsTotalTime;
    
	@Column(name="TIME_DESC")
    private  String timeDesc;
    
	
}
