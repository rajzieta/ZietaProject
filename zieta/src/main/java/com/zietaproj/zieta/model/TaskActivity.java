package com.zietaproj.zieta.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "TASK_ACTIVITY")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "created_date", "modified_date" }, allowGetters = true)
@Data
public class TaskActivity extends BaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "client_id")
	@NotNull
	private Long clientId;

	@Column(name = "project_id")
	@NotNull
	private Long projectId;

	@NotNull
	@Column(name = "task_id")
	private Long taskId;

	@NotNull
	private Long activity_id;
	
	@Column(nullable = true, name = "START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	@Column(nullable = true, name = "END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@Column(nullable=true, name= "PLANNED_HOURS", precision=10, scale=2)
	private float plannedHrs;
	
	@Column(nullable=true, name= "ACTUAL_HOURS", precision=10, scale=2)
	private float actualHrs;
	

	

}
