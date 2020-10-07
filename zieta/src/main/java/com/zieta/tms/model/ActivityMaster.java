package com.zieta.tms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



@Entity
@Table(name = "ACTIVITY_MASTER")
@Data
public class ActivityMaster extends BaseEntity implements Serializable{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Long activityId;

    @Column(name="client_id")
    private Long clientId;

    @Column(name = "activity_code")
    private String activityCode;
    
    @Column(name = "activity_desc")
    private String activityDesc;
    
    @Column(name = "is_active")
    private boolean active;
}
