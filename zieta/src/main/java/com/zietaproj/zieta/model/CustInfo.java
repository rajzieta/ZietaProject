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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


	@Entity
	@Table(name = "cust_info")
	@EntityListeners(AuditingEntityListener.class)
	@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
	        allowGetters = true)
	@Data
	public class CustInfo implements Serializable{

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    @Column(name = "client_id")
	    private Long clientId;

	    @NotBlank
	    private String cust_name;
	    
	    @NotBlank
	    private String cust_address;
	    
	    @NotBlank
	    private String cust_details;
	    
	    @NotNull
	    private String cust_code;
	    
	    
		@NotBlank
		private String created_by;

	    @Column(nullable = false, updatable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    @CreatedDate
	    private Date created_date;

	    @Column(nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    @LastModifiedDate
	    private Date modified_date;
		
		@NotBlank
		private String modified_by;
		
		private boolean IS_DELETE;

		
		

}