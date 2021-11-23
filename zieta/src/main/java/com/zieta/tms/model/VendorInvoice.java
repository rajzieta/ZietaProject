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
@Table(name = "vendor_invoice")
@Data
public class VendorInvoice extends BaseEntity implements Serializable{

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
    
    @Column(name="invoice_num")
    private String invoiceNum;
    
    @Column(name="invoice_date")
    private String invoiceDate;
    
    @Column(name="submit_date")
    private String submitDate;
    
    @Column(name="invoice_amt")
    private String invoiceAmt;
    
    @Column(name="invoice_currency")
    private String invoiceCurrency;    
    
    @Column(name="open_advance")
    private String openAdvance;
    
    @Column(name="adv_adjusment_remarks")
    private String advAdjustmentRemarks;
    
    @Column(name="supplier_state")
    private String supplierState;
    
    @Column(name="brief_remarks")
    private String briefRemarks;
    
    @Column(name="remarks")
    private String remarks;
    
    
   
   
    
   
}
