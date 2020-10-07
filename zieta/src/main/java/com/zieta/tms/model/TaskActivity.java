package com.zieta.tms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "TASK_ACTIVITY")
@Data
@EqualsAndHashCode(callSuper=true)
public class TaskActivity extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long taskActivityId;

	@Column(name = "client_id")
	private Long clientId;

	@Column(name = "project_id")
	private Long projectId;

	@Column(name = "task_id")
	private Long taskId;

	@Column(name = "activity_id")
	private Long activityId;
	
	@Column (name = "USER_ID")
	private Long userId;
	
	@Column(nullable = true, name = "START_DATE")
	private Date startDate;
	
	@Column(nullable = true, name = "END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(nullable=true, name= "PLANNED_HOURS", precision=10, scale=2)
	private float plannedHrs;
	
	@Column(nullable=true, name= "ACTUAL_HOURS", precision=10, scale=2)
	private float actualHrs;
	

	

}
