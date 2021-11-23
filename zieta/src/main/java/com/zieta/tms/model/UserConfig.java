package com.zieta.tms.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "user_config")
@EntityListeners(AuditingEntityListener.class)
@Data
public class UserConfig extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "orgnode")
	private Long orgnode;
	
	
	@Column(name = "reporting_mgr")
	private Long reportingMgr;
	
	@Column(name = "access_type_id")
	private Long accessTypeId;
	
	@Column(name = "exp_template_id")
	private Long expTemplateId;
	
	
	@Column(name = "ts_open")
	private int tsOpen;
	
	@Column(name = "exp_open")
	private Long expOpen;	
	
	
}
