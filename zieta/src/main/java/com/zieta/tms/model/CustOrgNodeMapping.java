package com.zieta.tms.model;

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
	public class CustOrgNodeMapping extends BaseEntity implements Serializable{

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
	    private Long custOrgNodeId;

	   
	    @Column(name = "client_id")
	    private Long clientId;

	    
	    @Column(name = "cust_id")
	    private Long custId;
	    
	    
	    @Column(name = "orgnode")
	    private Long orgNode;
	    
		

		
		

}
