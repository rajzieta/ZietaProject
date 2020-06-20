package com.zietaproj.zieta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@Table(name = "activity_user_mapping")
@EntityListeners(AuditingEntityListener.class)
@Data
public class ActivityUserMapping extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CLIENT_ID")
	@NotNull
	private Long clientId;

	@NotNull
	@Column(name = "PROJECT_ID")
	private Long projectId;

	@NotNull
	@Column(name = "TASK_ID")
	private Long taskId;

	@NotNull
	@Column(name = "ACTIVITY_ID")
	private Long activityId;

	@Column(name = "USER_ID", nullable=true)
	private Long userId;

}
