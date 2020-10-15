package com.zieta.tms.response;

import java.util.List;

import com.zieta.tms.model.ExpenseEntries;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseWorkflowRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseWFRDetailsForApprover {

	ExpenseWorkflowRequest expenseWorkflowRequest;

	ExpenseInfo expenseInfo;

	List<ExpenseEntries> expenseEntriesList;

	String projectName;
	String clientName;
	String requestorName;
	String wfActionType;
	String wfStateType;
	List<ExpenseWorkFlowComment> expenseWorkFlowComment;

}
