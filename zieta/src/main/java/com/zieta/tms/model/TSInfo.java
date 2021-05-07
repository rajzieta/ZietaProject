package com.zieta.tms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;



@Entity
@Table(name = "TS_INFO")
@Data
@EqualsAndHashCode(callSuper=false)
public class TSInfo extends BaseEntity implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name="client_id")
    private Long clientId;

	@Column(name="project_id")
    private Long projectId;
    
	@Column(name="activity_id")
    private Long activityId;
	
	@Column(name="task_activity_id")
    private Long taskActivityId;
    
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
	
	@Column(name="submit_date")
    private Date submitDate;
  
	@Column(name="ts_total_submitted_time")
    private  Float tsTotalSubmittedTime;
	
	@Column(name="ts_total_approved_time")
    private  Float tsTotalApprovedTime;
	
	
}
