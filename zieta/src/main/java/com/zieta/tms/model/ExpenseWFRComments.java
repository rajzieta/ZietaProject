package com.zieta.tms.model;

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
@Table(name = "expwf_request_comments")
@Data
public class ExpenseWFRComments {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="EXPWR_ID")
    private Long expWrId;

    @Column(name="EXP_ID")
    private Long expId;

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="action_date")
    private Date actionDate;
    
    
    @Column(name="approver_id")
    private Long approverId;
    
    @Column(name="comments")
    private String comments;
    
}
