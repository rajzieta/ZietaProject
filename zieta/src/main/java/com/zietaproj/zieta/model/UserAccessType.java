package com.zietaproj.zieta.model;

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
@Table(name = "user_accesstype_mapping")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
        allowGetters = true)
@Data
public class UserAccessType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "CLIENT_ID")
	@NotNull
	private Long clientId;
	
	@NotNull
	@Column( name = "USER_ID")
	private Long userId;
	
	@NotNull
	@Column( name = "ACCESS_TYPE_ID")
	private Long accessTypeId;
	
	@Column( name = "CREATED_BY")
	private String created_by;
	
	
	@Column(nullable = false, name = "CREATED_DATE" )
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
	private Date created_date;
	
	@Column( name = "MODIFIED_BY")
	private String modified_by;
	
	@Column(nullable = false, name="MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
	private Date modified_date;
	
	@Column( name = "IS_DELETE")
	private short is_delete;
	
	

}
