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
@Table(name = "EXPENSE_INFO")
@Data
public class ExpenseInfo extends BaseEntity implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name="id")
    private Long id;

    @Column(name="client_id")
    private Long clientId;
    
    @Column(name="user_id")
    private Long userId;
    
    @Column(name="project_id")
    private Long projectId;
    
    @Column(name="exp_heading")
    private String expHeading;
    
    @Column(name="exp_start_date")
    private Date expStartDate;
    
    @Column(name="exp_end_date")
    private Date expEndDate;
    
    @Column(name="exp_posting_date")
    private Date expPostingDate;
    
    @Column(name="exp_amount")
	private float expAmount;
    
    @Column(name="approved_amt")
	private float approvedAmt;
    
    @Column(name="status_id")
	private Long statusId;
}
