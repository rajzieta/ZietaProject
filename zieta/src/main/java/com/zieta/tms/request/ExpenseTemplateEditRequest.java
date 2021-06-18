package com.zieta.tms.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseTemplateEditRequest {
	
	    private Long id;
		private Long clientId;		
		private String templateName;
		
}
