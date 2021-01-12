package com.zieta.tms.dto;

import java.util.Date;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveInfoDTO extends BaseEntity {

	
	
	private Long id;
    private Long clientId;
    private Long userId;
    private String leaveDesc;
    private Long leaveType;
    private Date leaveStartDate;
    private Long startSession;
    private Date leaveEndDate;
    private Long endSession;
    private Long approverId;
    private String approverComments;
    private Long statusId;
}
