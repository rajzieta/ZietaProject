package com.zietaproj.zieta.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;



@Entity
@Table(name = "TS_INFO")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)

@Data
public class TSInfo extends BaseEntity implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Long tsInfoId;

	//@NotNull
	@Column(name="client_id")
    private Long clientId;

    //@NotBlank
	@Column(name="time_type")
    private String timeType;
    
	@Column(name="project_id")
    private Long projectInfoId;
    
	@Column(name="activity_id")
    private Long activityId;
    
	@Column(name="user_id")
    private Long userInfoId;
    
	@Column(name="task_id")
    private Long taskInfoId;
    
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
