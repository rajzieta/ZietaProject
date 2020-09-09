package com.zietaproj.zieta.model;

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
@Table(name = "wf_request_comments")
@Data
public class WorkFlowRequestComments {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="WR_ID")
    private Long wrId;

    @Column(name="ts_id")
    private Long tsId;

    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="action_date")
    private Date actionDate;
    
    
    @Column(name="approver_id")
    private Long approverId;
    
    @Column(name="comments")
    private String comments;
    
}
