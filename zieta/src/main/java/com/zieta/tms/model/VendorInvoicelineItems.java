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
@Table(name = "vendor_invoice_lineitems")
@Data
public class VendorInvoicelineItems extends BaseEntity implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
    private Long id;

	@Column(name="INV_ID")
	private Long Inv_Id;
	
	
	@Column(name="SERVICE_DATE")
	private Date Service_Date;
	
	@Column(name="EXP_TYPE")
	private Long Exp_Type;
	
	@Column(name="HSN_CODE")
	private String Hsn_Code;
	
	@Column(name="AMOUNT")
	private Double Amount;
	
	@Column(name="ALLOCATION_CODE")
	private String Allocation_Code;
	
	@Column(name="INTERNAL_ORDER")
	private String Internal_order;
	
	@Column(name="RECOVERABLE")
	private Boolean Recoverable;
	
	@Column(name="COST_CENTER")
	private String Cost_Center;
	
	@Column(name="REMARKS")
	private String Remarks;   
    
   
   
    
   
}
