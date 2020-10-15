package com.zieta.tms.response;

import com.zieta.tms.model.ExpenseWFRComments;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseWorkFlowComment {

	ExpenseWFRComments expenseWFRComments;
	String approverName;

}
