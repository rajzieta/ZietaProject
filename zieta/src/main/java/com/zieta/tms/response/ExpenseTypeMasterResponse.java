package com.zieta.tms.response;

import com.zieta.tms.model.ExpenseTypeMaster;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseTypeMasterResponse extends ExpenseTypeMaster {

	private String clientCode;
	private String clientDescription;
	
}
