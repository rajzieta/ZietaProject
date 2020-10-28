package com.zieta.tms.response;

import java.util.List;

import com.zieta.tms.dto.ExpenseEntryMetaData;
import com.zieta.tms.model.ExpenseInfo;
import com.zieta.tms.model.ExpenseWorkflowRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseWFRDetailsForApprover {

	ExpenseWorkflowRequest expenseWorkflowRequest;

	ExpenseInfo expenseInfo;

	List<ExpenseEntryMetaData> expenseEntries;

	String projectName;
	String clientName;
	String requestorName;
	String wfActionType;
	String wfStateType;
	String orgName;
	List<ExpenseWorkFlowComment> expenseWorkFlowComment;

}
