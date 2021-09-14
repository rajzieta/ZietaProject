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
@Table(name = "EMPLOYEE_ADVANCE")
@Data
public class EmployeeAdvance extends BaseEntity implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
    private Long id;

    
    @Column(name = "client_id")
	private Long clientId;
	
	@Column(name = "user_id")
	private Long userId;
	

	@Column(name = "company_codename")
	private String companyCodename;
	
	@Column(name = "company_name")
	private String companyName;
	
	
	@Column(name = "branch_code")
	private String branchCode;	
	
	@Column(name = "advance_req_date")
	private Date advanceReqDate;
	
	@Column(name = "advance_amt")
	private Long advanceAmt;	
	
	@Column(name = "adv_currency")
	private String advCurrency;
	
	@Column(name = "remarks")
	private String remarks;

}
