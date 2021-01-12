package com.zieta.tms.dto;

import javax.persistence.Column;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveTypeMasterDTO extends BaseEntity {

	
	  private Long id;
	   private Long clientId;
	    private String leaveType;
	    
}
