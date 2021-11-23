package com.zieta.tms.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.poi.hpsf.Decimal;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class VendorAdvanceDTO extends BaseEntity{

	
    private Long id;

    private Long clientId;
   
    private Long userId;
    
    private String companyCodename;
    
    private String companyName;
    
    private String branchCode;
    
    private String branchName;
    
    private String vendorCode;
    
    private String vendorName;
    
    private String proformaInvNum;
    
    private Date proformaInvDate;
    
    private Date submitDate;
    
    private float proformaInvAmt;
    
    private String proformaInvCurrency;
    
    private String supplierState;
   
    private String receipientState;
    
    private String remarks;
    
	
}
