package com.zieta.tms.dto;

import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date leaveStartDate;
    private Long startSession;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date leaveEndDate;
    private Long endSession;
    private Long approverId;
    private String approverComments;
    private Long statusId;
    private String leaveTypeDescription;
}
