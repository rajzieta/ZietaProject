package com.zieta.tms.dto;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseMasterDTO extends BaseEntity {

	private Long id;
	private Long clientId;
	private String expenseType;
	
}
