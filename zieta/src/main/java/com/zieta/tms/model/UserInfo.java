package com.zieta.tms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity
@Table(name = "user_info", uniqueConstraints=@UniqueConstraint(columnNames= {"client_id", "emp_id"}))
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
	
	@NotBlank	
	@Column(name = "EMAIL_ID", unique= true)
	private String email;
	
	@Column(name = "EMP_ID")
	private String empId;
	
	@Column(name = "ORGNODE")
	private Long orgNode;
	
	@Column(name = "ACCESS_TYPE_ID")
	private Long accessTypeId;
	
	@Column(name = "phone_no")
	private String phoneNo;
	
	@Column(name = "is_active")
	private short isActive;
	
	@Column(nullable = false)
	private String password;

}
