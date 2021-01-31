package com.zieta.tms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
@Entity
@Table(name = "leave_info")
@Data
public class LeaveInfo extends BaseEntity implements Serializable {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 //   @Column(name="id")
	    private Long id;

	   
	    @Column(name="client_id")
	    private Long clientId;
	    
	    @Column(name="user_id")
	    private Long userId;
	    
	    @Column(name="leave_desc")
	    private String leaveDesc;
	    
	    @Column(name="leave_type")
	    private Long leaveType;
	    
	    @Column(name = "leave_start_Date")
	    private Date leaveStartDate;	
	    
	    @Column(name="start_session")
	    private Long startSession;
	    
	    @Column(name="leave_end_Date")
	    private Date leaveEndDate;
	    
	    @Column(name="end_session")
	    private Long endSession;
	    
	    
	    @Column(name="approver_id")
	    private Long approverId;
	    
	    @Column(name="approver_comments")
	    private String approverComments;
	    
	    @Column(name="status_id")
	    private Long statusId;
}

