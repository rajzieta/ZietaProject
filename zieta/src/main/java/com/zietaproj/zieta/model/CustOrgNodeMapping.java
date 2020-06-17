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
	@Table(name = "cust_orgnode_mapping")
	@EntityListeners(AuditingEntityListener.class)
	@JsonIgnoreProperties(value = {"created_date", "modified_date"}, 
	        allowGetters = true)
	@Data
	public class CustOrgNodeMapping implements Serializable{

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank
	    @Column(name = "client_id")
	    private Long clientId;

	    @NotBlank
	    @Column(name = "cust_id")
	    private Long custId;
	    
	    @NotBlank
	    @Column(name = "orgnode")
	    private Long orgNode;
	    
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