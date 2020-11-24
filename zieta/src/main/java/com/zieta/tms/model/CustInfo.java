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
import javax.persistence.UniqueConstraint;
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
	public class CustInfo extends BaseEntity implements Serializable{

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
	    private Long custInfoId;

	   
	    @Column(name = "client_id")
	    private Long clientId;

	    @Column(name = "cust_name")
	    private String custName;
	    
	    @Column(name = "cust_address")
	    private String custAddress;
	    
	    @Column(name = "cust_details")
	    private String custDetails;
	    
	  //  @NotBlank
	//    @Column(name = "cust_code")
	 //   private String custCode;
	    
	   

	    

		
		

}
