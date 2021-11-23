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
@Table(name = "vendor_advance")
@Data
public class VendorAdvance extends BaseEntity implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
    private Long id;

    @Column(name="client_id")
    private Long clientId;
    
    @Column(name="user_id")
    private Long userId;
    
    @Column(name="company_codename")
    private String companyCodename;
    
    @Column(name="company_name")
    private String companyName;
    
    @Column(name="branch_code")
    private String branchCode;
    
    @Column(name="branch_name")
    private String branchName;
    
    @Column(name="vendor_code")
    private String vendorCode;
    
    @Column(name="vendor_name")
    private String vendorName;
    
    @Column(name="proforma_inv_num")
    private String proformaInvNum;
    
    @Column(name="proforma_inv_date")
    private Date proformaInvDate;
    
    @Column(name="submit_date")
    private Date submitDate;
    
    @Column(name="proforma_inv_amt")
    private float proformaInvAmt;
    
    @Column(name="proforma_inv_currency")
    private String proformaInvCurrency;
    
    @Column(name="supplier_state")
    private String supplierState;
    
    @Column(name="receipient_state")
    private String receipientState;
    
    @Column(name="remarks")
    private String remarks;
    
    
   
   
    
   
}
