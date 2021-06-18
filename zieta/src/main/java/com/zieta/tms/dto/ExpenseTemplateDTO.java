package com.zieta.tms.dto;

import java.util.Date;

import com.zieta.tms.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ExpenseTemplateDTO extends BaseEntity{

	private Long id;
	private Long clientId;
	private String templateName;
	
	
}
