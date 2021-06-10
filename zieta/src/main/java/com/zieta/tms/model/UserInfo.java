package com.zieta.tms.model;

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
@Table(name = "user_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"email_id"})})
//@Unique(columns = { @UniqueColumn(fields= "email_id")})
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)
@Data
public class UserInfo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "exp_template_id")
	private Long expTemplateId;
	
	@Column(name = "client_id")
	private Long clientId;
	

	@Column(name = "user_fname")
	private String userFname;
	
	@Column(name = "user_mname")
	private String userMname;
	
	
	@Column(name = "user_lname")
	private String userLname;
	
	@NotBlank	
	//@Email
	@Column(name = "EMAIL_ID", unique=true, updatable = false)
	private String email;
	
	@Column(name = "EMP_ID")
	private String empId;
	
	@Column(name = "ORGNODE")
	private Long orgNode;
	
	@Column(name = "REPORTING_MGR")
	private Long reportingMgr;
	
	@Column(name = "ACCESS_TYPE_ID")
	private Long accessTypeId;
	
	@Column(name = "phone_no")
	private String phoneNo;
	
	@Column(name = "is_active")
	private short isActive;
	
	@Column(nullable = false)
	private String password;

	@Column(name = "TS_OPEN")
	private Short isTsOpen =0;
	
	@Column(name = "EXP_OPEN")
	private Short isExpOpen =0;
	
	@Column(name = "EXT_ID")
	private String extId;

}
