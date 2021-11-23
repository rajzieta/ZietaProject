package com.zieta.tms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "vendor_adv_lineitems")
@Data
public class VendorAdvanceLineItems extends BaseEntity implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
    private Long id;

	@Column(name="VENDOR_ADV_ID")
	private Long vendorAdvId;
	
	@Column(name="EXP_TYPE")
	private Long ExpType;
	
	@Column(name="HSN_CODE")
	private String HsnCode;
	
	@Column(name="AMOUNT")
	private long amount;
	
	@Column(name="REMARKS")
	private String Remarks;   
    
   
   
    
   
}
