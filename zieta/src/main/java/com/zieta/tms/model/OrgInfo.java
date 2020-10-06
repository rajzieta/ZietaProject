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
@Table(name = "orgunit_info")
@Data
public class OrgInfo extends BaseEntity implements Serializable {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name="id")
	    private Long orgUnitId;

	   
	    @Column(name="client_id")
	    private Long clientId;
	
	    @Column(name="org_node_code")
	    private String orgNodeCode;
	  
	    @Column(name="org_node_name")
	    private String orgNodeName;
	    
	    @Column(name="org_type")
	    private Long orgType;
	   
	    @Column(name="org_parent_id")
	    private Long orgParentId;
	    
	   
	    @Column(name="org_status")
	    private Long orgStatus;
	    
		


		
		
	
	
}
