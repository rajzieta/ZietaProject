package com.zieta.tms.model;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "user_info")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)
@Data
public class UserInfo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "client_id")
	private Long clientId;
	

	@Column(name = "user_fname")
	private String userFname;
	
	@Column(name = "user_mname")
	private String userMname;
	
	
	@Column(name = "user_lname")
	private String userLname;
	
	@Column(name = "EMAIL_ID")
	private String email;
	
	@Column(name = "EMP_ID")
	private String empId;
	
	@Column(name = "ACCESS_TYPE_ID")
	private Long accessTypeId;
	
	@Column(name = "phone_no")
	private String phoneNo;
	
	@Column(name = "is_active")
	private short isActive;
	
//	private String created_by;
//	
//	@Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedDate
//	private Date created_date;
//	
//	private String modified_by;
//	
//	@Column(nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @LastModifiedDate
//	private Date modified_date;
//	
//	private short is_delete;
	
	//@NotBlank
	
	@Column(nullable = false)
	private String password;

}
